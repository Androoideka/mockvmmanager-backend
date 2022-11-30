package io.github.androoideka.vm_manager.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import io.github.androoideka.vm_manager.mappers.UserMapper;
import io.github.androoideka.vm_manager.repositories.UserRepository;
import io.github.androoideka.vm_manager.services.UserService;
import io.github.androoideka.vm_manager.services.UserServiceDefaultImplementation;

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
