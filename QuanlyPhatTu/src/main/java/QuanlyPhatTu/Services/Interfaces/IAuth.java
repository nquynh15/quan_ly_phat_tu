package QuanlyPhatTu.Services.Interfaces;

import QuanlyPhatTu.Request.JwtResponse;
import QuanlyPhatTu.Request.RequestLogin;
import QuanlyPhatTu.Request.RequestRegister;

public interface IAuth {
    public String Register(RequestRegister resquestRegister);

    public JwtResponse login(RequestLogin requestLogin);
}
