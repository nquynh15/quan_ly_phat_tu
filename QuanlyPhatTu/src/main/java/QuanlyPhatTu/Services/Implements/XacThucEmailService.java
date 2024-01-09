package QuanlyPhatTu.Services.Implements;

import QuanlyPhatTu.Entities.PhatTu;
import QuanlyPhatTu.Entities.XacNhanEmail;
import QuanlyPhatTu.Repositories.PhatTuRepo;
import QuanlyPhatTu.Repositories.XacNhanEmailRepo;
import QuanlyPhatTu.Services.Interfaces.IXacThucEmail;
import QuanlyPhatTu.plugin.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.Optional;
import java.util.Random;

@Service
public class XacThucEmailService implements IXacThucEmail {
    @Autowired
    private XacNhanEmailRepo xacNhanEmailRepo;
    @Autowired
    private PhatTuRepo phatTuRepo;

    @Override
    public boolean verifyAccount(String enteredCode) throws Exception{
        // Kiểm tra mã xác nhận

        Optional<XacNhanEmail> xacNhanEmailTest = xacNhanEmailRepo.findAllByMaXacNhan(enteredCode);
        if (!xacNhanEmailTest.isEmpty()) {
            //return "Account verified successfully!";
            XacNhanEmail xacNhanEmail = xacNhanEmailTest.get();
            PhatTu phatTu = phatTuRepo.findById(xacNhanEmail.getPhatTu().getId()).get();
            if(xacNhanEmail.getThoigianHethan().compareTo(LocalTime.now())>=0){
                xacNhanEmail.setDaXacNhan(true);
                phatTu.active = true;
                phatTuRepo.save(phatTu);
            }
            else{
                String verificationCode = GenerateRandomCode();
                Email e = new Email("nguyenquynh170820@gmail.com", "taoypvnczzcrnmok");
                e.sendContentToVer2(phatTu.getEmail(), "Email xác thực: ", verificationCode);

                XacNhanEmail xacNhanEmail1 = new XacNhanEmail();
                xacNhanEmail1.setPhatTu(phatTu);
                xacNhanEmail1.setMaXacNhan(verificationCode);
                xacNhanEmail1.setThoigianHethan(LocalTime.now().plusSeconds(60));
                xacNhanEmailRepo.save(xacNhanEmail1);
                throw new Exception("Quá thời gian xác nhận, mời nhập mã mới");
            }
            return true;
        } else {
            //return "Invalid verification code. Account verification failed.";
            return false;
        }
    }

    private String GenerateRandomCode(){
        Random random = new Random();
        int code = 100000 + random.nextInt(999999);
        return String.valueOf(code);
    }

    @Override
    public String sendverificationEmail() {
        String verifyCode = GenerateRandomCode();
        XacNhanEmail xacNhanEmail = new XacNhanEmail();
        xacNhanEmail.setMaXacNhan(verifyCode);
        xacNhanEmail.setThoigianHethan(LocalTime.now().plusMinutes(1));
        xacNhanEmailRepo.save(xacNhanEmail);

        Email e = new Email("nguyenquynh170820@gmail.com", "taoypvnczzcrnmok");
        e.sendContentToVer2("nguyenquynh170602@gmail.com","verifyCode: ",verifyCode);
        return "Đã gửi mã xác nhận, vui lòng kiểm tra hòm thư";
    }
}
