package rs.raf.agasic218rn.nwpprojekatbeagasic218rn.runners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import rs.raf.agasic218rn.nwpprojekatbeagasic218rn.model.PermissionList;
import rs.raf.agasic218rn.nwpprojekatbeagasic218rn.model.User;
import rs.raf.agasic218rn.nwpprojekatbeagasic218rn.repositories.UserRepository;

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
        PermissionList permissionList = new PermissionList();
        permissionList.setCanCreateUsers(true);
        permissionList.setCanReadUsers(true);
        permissionList.setCanDeleteUsers(true);
        permissionList.setCanUpdateUsers(true);
        user.setPermissionList(permissionList);
        this.userRepository.save(user);
    }
}
