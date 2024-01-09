package QuanlyPhatTu.Controllers;

import QuanlyPhatTu.Repositories.XacNhanEmailRepo;
import QuanlyPhatTu.plugin.Email;
import QuanlyPhatTu.Services.Implements.XacThucEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@RestController
@RequestMapping(value = "api/email")
public class EmailController {
    @Autowired
    private XacNhanEmailRepo xacNhanEmailRepo;
    @Autowired
    private XacThucEmailService xacthucEmailService;

    @PostMapping(value = "/send")
    public String sendVerificationEmail(){
        return xacthucEmailService.sendverificationEmail();
    }
    @GetMapping(value = "/verify")
    public  String verifyAccount(@RequestParam String entercode){
        try {
            if(xacthucEmailService.verifyAccount(entercode))
                return "Xác thực thành công";
            else
                return "Xác thực thất bại";
        } catch (Exception e) {
            return "Xác thực thất bại";
        }
    }
    private String GenerateRandomCode(){
        Random random = new Random();
        int code = 100000 + random.nextInt(999999);
        return String.valueOf(code);
    }

}
