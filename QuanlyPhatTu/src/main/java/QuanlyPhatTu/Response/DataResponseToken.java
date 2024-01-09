package QuanlyPhatTu.Response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataResponseToken {
    public String accessToken;
    public String refreshToken;

    public DataResponseToken(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
