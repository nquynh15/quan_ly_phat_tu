package QuanlyPhatTu.Services.Implements;

import QuanlyPhatTu.Entities.PhatTu;
import QuanlyPhatTu.Entities.XacNhanEmail;
import QuanlyPhatTu.Repositories.PhatTuRepo;
import QuanlyPhatTu.Repositories.XacNhanEmailRepo;
import QuanlyPhatTu.Request.JwtResponse;
import QuanlyPhatTu.Request.RequestLogin;
import QuanlyPhatTu.Request.RequestRegister;
import QuanlyPhatTu.Security.Service.UserDetailsImpl;
import QuanlyPhatTu.Security.jwt.JwtUtils;
import QuanlyPhatTu.Services.Interfaces.IAuth;
import QuanlyPhatTu.plugin.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class AuthService implements IAuth {
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

    public static boolean isValidEmail(String email){
        //String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\\\.[a-zA-Z0-9_+&*-]+)*@gmail\\\\.com$";
        //String emailRegex = "^[A-Za-z0-9]+[A-Za-z0-9]*@[A-Za-z0-9]+(\\\\.[A-Za-z0-9]+)$";
        String emailRegex = "^[A-Za-z0-9]+[A-Za-z0-9]*@gmail.com$";

        Pattern pattern = Pattern.compile(emailRegex);

        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }

    @Override
    public String Register(RequestRegister resquestRegister) {
        Optional<PhatTu> phatTuTest = phatTuRepo.findAllByUsername(resquestRegister.username);
        if(resquestRegister.username.isEmpty() || resquestRegister.password.isEmpty() || resquestRegister.email.isEmpty())
            return "Phải điền đầy đủ thông tin đăng ký";
        if(!phatTuTest.isEmpty())
            return "tenTaiKhoan đã tồn tại";
        if(!isValidEmail(resquestRegister.email))
            return "Bạn phải nhập đúng định dạng email";
        PhatTu phatTu = new PhatTu();
        phatTu.setUsername(resquestRegister.username);
        phatTu.setEmail(resquestRegister.email);
        phatTu.setUpdateTime(LocalDate.now());
        //encoderpasswrod
        phatTu.setPassword(passwordEncoder.encode(resquestRegister.password));
        phatTu.active = false;

        String verificationCode = GenerateRandomCode();
        Email e = new Email("nguyenquynh170820@gmail.com", "taoypvnczzcrnmok");
        e.sendContentToVer2(resquestRegister.email, "Email xác thực", verificationCode);
        phatTuRepo.save(phatTu);

        XacNhanEmail xacNhanEmail = new XacNhanEmail();
        xacNhanEmail.setPhatTu(phatTu);
        xacNhanEmail.setMaXacNhan(verificationCode);
        xacNhanEmail.setThoigianHethan(LocalTime.now().plusSeconds(60));
        xacNhanEmailRepo.save(xacNhanEmail);

        return "Dang ky thanh cong";
    }

    @Override
    public JwtResponse login(RequestLogin requestLogin) {
        Optional<PhatTu> phatTuTest = phatTuRepo.findAllByUsername(requestLogin.username);
        if(phatTuTest.isEmpty())
            throw new IllegalArgumentException("user not found!");
        PhatTu phatTu = phatTuTest.get();
        if(!passwordEncoder.matches(requestLogin.password, phatTu.getPassword()))
            throw new IllegalArgumentException("password is incorrect!");

        refreshTokenService.saveRefreshTokenToDatabase(phatTu.getId());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(requestLogin.username, requestLogin.password));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        return new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles);
    }
    private String GenerateRandomCode(){
        Random random = new Random();
        int code = 100000 + random.nextInt(999999);
        return String.valueOf(code);
    }
}
