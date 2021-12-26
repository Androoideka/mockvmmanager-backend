package rs.raf.agasic218rn.domaci3beagasic218rn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import rs.raf.agasic218rn.domaci3beagasic218rn.mappers.UserMapper;
import rs.raf.agasic218rn.domaci3beagasic218rn.responses.PermissionListResponse;
import rs.raf.agasic218rn.domaci3beagasic218rn.model.User;
import rs.raf.agasic218rn.domaci3beagasic218rn.repositories.UserRepository;
import rs.raf.agasic218rn.domaci3beagasic218rn.requests.UserRequest;
import rs.raf.agasic218rn.domaci3beagasic218rn.responses.UserResponse;

import java.util.List;

public class UserServiceDefaultImplementation implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceDefaultImplementation(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = this.userRepository.findByEmail(email);
        if(user == null) {
            throw new UsernameNotFoundException("User with email " + email + " could not be found.");
        }
        PermissionListResponse permissionListResponse = new PermissionListResponse(user.getPermissionList());
        List<GrantedAuthority> grantedAuthorities = permissionListResponse.toSpringAuthorities();
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), grantedAuthorities);
    }

    @Override
    public void create(UserRequest userRequest) {
        this.userRepository.save(this.userMapper.UserRequestToUser(userRequest));
    }

    @Override
    public Page<UserResponse> list(Integer page, Integer size) {
        return this.userRepository.findAll(PageRequest.of(page, size)).map(userMapper::UserToUserResponse);
    }

    @Override
    public UserResponse view(Long userId) {
        User user = this.userRepository
                .findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("Cannot view because the user does not exist."));
        return this.userMapper.UserToUserResponse(user);
    }

    @Override
    public void edit(UserRequest userRequest) {
        User user = this.userRepository
                .findById(userRequest.getUserId())
                .orElseThrow(() -> new UsernameNotFoundException("Cannot save changes because the user does not exist."));
        User newUser = userMapper.UserRequestToUser(userRequest);
        newUser.setUserId(userRequest.getUserId());
        this.userRepository.save(newUser);
    }

    @Override
    public void delete(Long id) {
        User user = this.userRepository
                .findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Cannot complete the delete operation because the user does not exist."));
        this.userRepository.delete(user);
    }
}
