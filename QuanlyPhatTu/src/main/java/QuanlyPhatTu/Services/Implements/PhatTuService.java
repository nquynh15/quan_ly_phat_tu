package QuanlyPhatTu.Services.Implements;

import QuanlyPhatTu.Entities.PhatTu;
import QuanlyPhatTu.Entities.XacNhanEmail;
import QuanlyPhatTu.Repositories.XacNhanEmailRepo;
import QuanlyPhatTu.Request.RequestChangePass;
import QuanlyPhatTu.Security.jwt.JwtUtils;
import QuanlyPhatTu.plugin.Email;
import QuanlyPhatTu.Repositories.PhatTuRepo;
import QuanlyPhatTu.Services.Interfaces.IPhatTu;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.lang.String;

@Service
public class PhatTuService implements IPhatTu {
    @Autowired
    private PhatTuRepo phatTuRepo;
    @Autowired
    private XacNhanEmailRepo xacNhanEmailRepo;
    @Autowired
    private XacThucEmailService xacThucEmailService;
    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    PasswordEncoder passwordEncoder;

    private static final String SECRET_KEY = "somethingjustlikethis";

    public static boolean isValidEmail(String email){
        //String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\\\.[a-zA-Z0-9_+&*-]+)*@gmail\\\\.com$";
        //String emailRegex = "^[A-Za-z0-9]+[A-Za-z0-9]*@[A-Za-z0-9]+(\\\\.[A-Za-z0-9]+)$";
        String emailRegex = "^[A-Za-z0-9]+[A-Za-z0-9]*@gmail.com$";

        Pattern pattern = Pattern.compile(emailRegex);

        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }
    private String GenerateRandomCode(){
        Random random = new Random();
        int code = 100000 + random.nextInt(999999);
        return String.valueOf(code);
    }


    public Optional<PhatTu> LayDS(String username){
        Optional<PhatTu> phatTuTest = phatTuRepo.findAllByUsername(username);
        if(phatTuTest.isEmpty())
            return null;
        else
            return phatTuTest;
    }

    @Override
    public String ChangePassword(RequestChangePass requestChangePass) {
        Optional<PhatTu> phatTuTest = phatTuRepo.findAllByUsername(requestChangePass.username);
        if(phatTuTest.isEmpty())
            return "Không tìm thấy tên tài khoản của bạn!";
        try {
            if(!xacThucEmailService.verifyAccount(requestChangePass.enterCode))
                return "Mã xác nhận sai, mời nhập lại!";
        } catch (Exception e){
            return e.getMessage();
        }

        PhatTu phatTu = phatTuTest.get();
        phatTu.setPassword(requestChangePass.newPassword);
        phatTuRepo.save(phatTu);
        return "Đổi mật khẩu thành công";

    }

    @Override
    public String ForgetPassword(String username) {
        Optional<PhatTu> phatTuTest = phatTuRepo.findAllByUsername(username);
        if(phatTuTest.isEmpty())
            return "Không tìm thấy tên tài khoản của bạn!";
        PhatTu phatTu = phatTuTest.get();
        String email = phatTu.getEmail();

        //gửi mail lấy mã
        String verificationCode = GenerateRandomCode();
        Email e = new Email("nguyenquynh170820@gmail.com", "taoypvnczzcrnmok");
        e.sendContentToVer2(email, "Email xác thực", verificationCode);

        XacNhanEmail xacNhanEmail = new XacNhanEmail();
        xacNhanEmail.setPhatTu(phatTu);
        xacNhanEmail.setMaXacNhan(verificationCode);
        xacNhanEmail.setThoigianHethan(LocalTime.now().plusSeconds(60));
        xacNhanEmailRepo.save(xacNhanEmail);

        return "Gửi mã xác nhận thành công, vui lòng kiểm tra email!";
    }


    @Override
    public List<PhatTu> filterByUsername(String username, Pageable page) {
        List<PhatTu> phatTuList = new ArrayList<>();
        for (PhatTu phatTu: phatTuRepo.findAll()) {
            if(phatTu.getUsername().equals(username))
                phatTuList.add(phatTu);
        }

        int pageNumber = page.getPageNumber();
        int pageSize = page.getPageSize();
        int fromIndex = pageNumber * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, phatTuList.size());
        if (fromIndex >= phatTuList.size()) {
            return Collections.emptyList(); // Trả về danh sách rỗng nếu trang không có dữ liệu
        }
        return phatTuList.subList(fromIndex, toIndex);
    }

    @Override
    public List<PhatTu> filterByEmail(String email, Pageable page) {
        List<PhatTu> list = new ArrayList<>();
        for (PhatTu phatTu: phatTuRepo.findAll()) {
            if(phatTu.getEmail().equals(email))
                list.add(phatTu);
        }
        int pageNumber = page.getPageNumber();
        int pagesize = page.getPageSize();
        int fromIndex = pageNumber * pagesize;
        int toIndex = Math.min(fromIndex + pagesize, list.size());

        if(fromIndex >= list.size())
            return Collections.emptyList();
        return list.subList(fromIndex, toIndex);
    }

    @Override
    public List<PhatTu> filterByGender(String gender, Pageable page) {
        List<PhatTu> list = new ArrayList<>();
        for (PhatTu phatTu: phatTuRepo.findAll()) {
            if(phatTu.getGender().equals(gender))
                list.add(phatTu);
        }
        int pageNumber = page.getPageNumber();
        int pagesize = page.getPageSize();
        int fromIndex = pageNumber * pagesize;
        int toIndex = Math.min(fromIndex + pagesize, list.size());

        if(fromIndex >= list.size())
            return Collections.emptyList();
        return list.subList(fromIndex, toIndex);
    }
}
