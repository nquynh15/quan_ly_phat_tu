package QuanlyPhatTu.Security.Service;

import QuanlyPhatTu.Entities.PhatTu;
import QuanlyPhatTu.Repositories.PhatTuRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    PhatTuRepo phatTuRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        PhatTu user = phatTuRepo.findAllByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
        return UserDetailsImpl.build(user);
    }
}
