package rs.raf.agasic218rn.domaci3beagasic218rn.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import rs.raf.agasic218rn.domaci3beagasic218rn.model.PermissionList;
import rs.raf.agasic218rn.domaci3beagasic218rn.responses.PermissionListResponse;
import rs.raf.agasic218rn.domaci3beagasic218rn.model.User;
import rs.raf.agasic218rn.domaci3beagasic218rn.requests.UserRequest;
import rs.raf.agasic218rn.domaci3beagasic218rn.responses.UserResponse;

@Component
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponse UserToUserResponse(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setEmail(user.getEmail());
        userResponse.setName(user.getName());
        userResponse.setSurname(user.getSurname());
        userResponse.setPermissionListResponse(new PermissionListResponse(user.getPermissionList()));
        return userResponse;
    }

    public User UserRequestToUser(UserRequest userRequest) {
        User user = new User();
        user.setEmail(userRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setName(userRequest.getName());
        user.setSurname(userRequest.getSurname());
        PermissionList permissionList = new PermissionList();
        permissionList.setCanCreateUsers(userRequest.getPermissionList().canCreateUsers());
        permissionList.setCanReadUsers(userRequest.getPermissionList().canReadUsers());
        permissionList.setCanUpdateUsers(userRequest.getPermissionList().canUpdateUsers());
        permissionList.setCanDeleteUsers(userRequest.getPermissionList().canDeleteUsers());
        user.setPermissionList(permissionList);
        return user;
    }
}
