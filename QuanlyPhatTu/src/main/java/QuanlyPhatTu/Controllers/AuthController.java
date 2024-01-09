package QuanlyPhatTu.Controllers;

import QuanlyPhatTu.Request.JwtResponse;
import QuanlyPhatTu.Request.RequestLogin;
import QuanlyPhatTu.Request.RequestRegister;
import QuanlyPhatTu.Services.Implements.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @PostMapping(value = "/register")
    public String register(@RequestBody RequestRegister requestRegister){

        //todo input cua postMethod: @requestbody
        return authService.Register(requestRegister);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<JwtResponse> login(@RequestBody RequestLogin requestLogin){
        try{
            JwtResponse jwtResponse = authService.login(requestLogin);
            return ResponseEntity.ok().body(jwtResponse);
        } catch (IllegalArgumentException exception){
            return ResponseEntity.badRequest().body(new JwtResponse(exception.getMessage()));
        }
    }
}
