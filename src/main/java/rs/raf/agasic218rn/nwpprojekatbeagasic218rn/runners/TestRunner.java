package rs.raf.agasic218rn.nwpprojekatbeagasic218rn.runners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import rs.raf.agasic218rn.nwpprojekatbeagasic218rn.configuration.PermissionUtil;
import rs.raf.agasic218rn.nwpprojekatbeagasic218rn.model.User;
import rs.raf.agasic218rn.nwpprojekatbeagasic218rn.repositories.UserRepository;

import java.util.Arrays;

@Component
public class TestRunner implements CommandLineRunner {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public TestRunner(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        User user = new User();
        user.setEmail("agasic218rn@raf.rs");
        user.setName("Andrej");
        user.setSurname("Gasic");
        user.setPassword(this.passwordEncoder.encode("tianming"));
        user.getPermissions().addAll(Arrays.asList(PermissionUtil.REPRESENTATIONS));
        this.userRepository.save(user);
    }
}
