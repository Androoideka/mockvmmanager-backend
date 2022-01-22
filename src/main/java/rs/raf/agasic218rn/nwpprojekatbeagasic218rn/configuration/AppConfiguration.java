package rs.raf.agasic218rn.nwpprojekatbeagasic218rn.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import rs.raf.agasic218rn.nwpprojekatbeagasic218rn.mappers.UserMapper;
import rs.raf.agasic218rn.nwpprojekatbeagasic218rn.repositories.UserRepository;
import rs.raf.agasic218rn.nwpprojekatbeagasic218rn.services.UserService;
import rs.raf.agasic218rn.nwpprojekatbeagasic218rn.services.UserServiceDefaultImplementation;

@Configuration
@EnableScheduling
@EnableAsync
public class AppConfiguration {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserService userService(UserRepository userRepository, UserMapper userMapper) {
        return new UserServiceDefaultImplementation(userRepository, userMapper);
    }
}
