package QuanlyPhatTu.Services.Interfaces;
import QuanlyPhatTu.Entities.PhatTu;
import QuanlyPhatTu.Request.JwtResponse;
import QuanlyPhatTu.Request.RequestChangePass;
import QuanlyPhatTu.Request.RequestLogin;
import QuanlyPhatTu.Request.RequestRegister;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IPhatTu {
    public String ChangePassword(RequestChangePass requestChangePass);
    public String ForgetPassword(String username);
    public List<PhatTu> filterByUsername(String username, Pageable page);

    public List<PhatTu> filterByEmail(String email, Pageable page);
    public List<PhatTu> filterByGender(String gender, Pageable page);
}
