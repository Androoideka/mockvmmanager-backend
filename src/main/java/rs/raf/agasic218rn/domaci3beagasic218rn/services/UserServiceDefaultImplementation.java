package rs.raf.agasic218rn.domaci3beagasic218rn.services;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import rs.raf.agasic218rn.domaci3beagasic218rn.model.PermissionList;
import rs.raf.agasic218rn.domaci3beagasic218rn.model.PermissionListDTO;
import rs.raf.agasic218rn.domaci3beagasic218rn.model.User;
import rs.raf.agasic218rn.domaci3beagasic218rn.repositories.UserRepository;
import rs.raf.agasic218rn.domaci3beagasic218rn.requests.UserRequest;

import java.util.List;

public class UserServiceDefaultImplementation implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceDefaultImplementation(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = this.userRepository.findByEmail(email);
        if(user == null) {
            throw new UsernameNotFoundException("User with email " + email + " could not be found.");
        }
        PermissionListDTO permissionListDTO = new PermissionListDTO(user.getPermissionList());
        List<GrantedAuthority> grantedAuthorities = permissionListDTO.toSpringAuthorities();
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), grantedAuthorities);
    }

    @Override
    public void createUser(UserRequest userRequest) {
        User user = new User();
        user.setEmail(userRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setName(userRequest.getName());
        user.setSurname(userRequest.getSurname());
        PermissionList permissionList = new PermissionList();
        permissionList.setCanCreateUsers(userRequest.getPermissionListDTO().canCreateUsers());
        permissionList.setCanReadUsers(userRequest.getPermissionListDTO().canReadUsers());
        permissionList.setCanUpdateUsers(userRequest.getPermissionListDTO().canUpdateUsers());
        permissionList.setCanDeleteUsers(userRequest.getPermissionListDTO().canDeleteUsers());
        user.setPermissionList(permissionList);
        this.userRepository.save(user);
    }
}
