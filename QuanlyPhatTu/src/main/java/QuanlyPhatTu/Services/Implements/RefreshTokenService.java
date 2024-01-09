package QuanlyPhatTu.Services.Implements;

import QuanlyPhatTu.Entities.RefreshToken;
import QuanlyPhatTu.Entities.Token;
import QuanlyPhatTu.Repositories.RefreshTokenRepo;
import QuanlyPhatTu.Repositories.TokenRepo;
import QuanlyPhatTu.Services.Interfaces.IRefreshToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Optional;

@Service
public class RefreshTokenService implements IRefreshToken {
    @Autowired
    private RefreshTokenRepo refreshTokenRepo;
    @Autowired
    private TokenRepo tokenRepo;

    // Độ dài của refresh token (tùy chọn)
    private static final int REFRESH_TOKEN_LENGTH = 32;

    private String generateRefreshToken() {
        // Tạo một mảng byte để lưu trữ dữ liệu ngẫu nhiên
        byte[] randomBytes = new byte[REFRESH_TOKEN_LENGTH];

        // Sử dụng SecureRandom để tạo dữ liệu ngẫu nhiên
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(randomBytes);

        // Mã hóa dữ liệu ngẫu nhiên thành chuỗi Base64
        String refreshToken = Base64.getEncoder().encodeToString(randomBytes);

        return refreshToken;
    }

    @Override
    public void saveRefreshTokenToDatabase(int accountId) {
        Optional<Token> tokenTest = tokenRepo.findAllByPtId(accountId);

        RefreshToken newToken = new RefreshToken();
        newToken.setToken(generateRefreshToken());
        refreshTokenRepo.save(newToken);
    }
}
