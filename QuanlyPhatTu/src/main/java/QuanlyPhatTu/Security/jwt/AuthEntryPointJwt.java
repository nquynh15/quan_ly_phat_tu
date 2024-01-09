package QuanlyPhatTu.Security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {
    //xử lý các ngoại lệ xác thực và trả về một phản hồi chuẩn xác thực khi người dùng không được ủy quyền.
    private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        logger.error("Unauthorized error: {}", authException.getMessage());

        //thiết lập các thuộc tính của phản hồi HTTP
        response.setContentType(MediaType.APPLICATION_JSON_VALUE); // kiểu nội dung của phản hồi là Json
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);    //mã trạng thái của phản hồi là 401 Unauthorized.

        //Tạo đối tượng để biểu diễn thông tin phản hồi
        final Map<String, Object> body = new HashMap<>();
        body.put("status", HttpServletResponse.SC_UNAUTHORIZED);
        body.put("error", "Unauthorized");
        body.put("message", authException.getMessage());
        body.put("path", request.getServletPath());

        //chuyển đổi đối tượng Map thnahf dữ liệu JSon và ghi vào 'OutputStream' của phản hồi
        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), body);
    }
}