package QuanlyPhatTu.Config;

import QuanlyPhatTu.Security.Service.UserDetailsServiceImpl;
import QuanlyPhatTu.Security.jwt.AuthEntryPointJwt;
import QuanlyPhatTu.Security.jwt.AuthTokenFilter;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.HashMap;
import java.util.Map;

import static QuanlyPhatTu.Entities.ERole.ROLE_ADMIN;
import static org.springframework.http.HttpMethod.GET;

@Configuration
@EnableMethodSecurity
public class WebSecurityConfig {

    @Value("${api.prefix}")
    String apiPrefix;
    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth.requestMatchers("api/quan-ly-phat-tu/filter-by-email").permitAll()
                        //.requestMatchers(String.format("%s/quan-ly-phat-tu/**", apiPrefix)).permitAll()
//                        .requestMatchers("/cloudinary/upload").permitAll()
//                        .requestMatchers(GET, String.format("%s/forms**", apiPrefix)).hasAnyRole("ROLE_ADMIN")
                        .requestMatchers("/api/auth/register/**").permitAll()
                        .requestMatchers("/api/auth/login/**").permitAll()
                        .requestMatchers("/api/quan-ly-phat-tu/images/**").permitAll()
                        .requestMatchers("/api/quan-ly-phat-tu/change-password/**").permitAll()
                        .requestMatchers("/api/quan-ly-phat-tu/forget-password/**").permitAll()
                        .requestMatchers("/api/quan-ly-phat-tu/upload-image/**").permitAll()
                        .requestMatchers("/api/email/verify/**").permitAll()
                        .requestMatchers("/api/email/send").permitAll()
                        .requestMatchers("/api/dao-trang/add/**").permitAll()
                        .requestMatchers("/api/dao-trang/update/**").permitAll()
                        .requestMatchers("/api/form/approve").hasAnyAuthority("ROLE_ADMIN")
                        .requestMatchers("/api/form/submit/**").permitAll()
                        .requestMatchers("/api/posts/**").permitAll()
                        .requestMatchers("/api/comment/**").permitAll()
                        .anyRequest().authenticated());


        http.authenticationProvider(authenticationProvider());

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public Cloudinary getCloudinary(){
        Map config = new HashMap();
        config.put("cloud_name", "dgwqaonc3");
        config.put("api_key", "683526996472698");
        config.put("api_secret", "XtunLVSzGKja9ae5iivuzs_tK_8");
        config.put("secure", true);
        return new Cloudinary(config);
    }
}
