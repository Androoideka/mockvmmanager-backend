package io.github.androoideka.vm_manager.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import io.github.androoideka.vm_manager.model.User;
import io.github.androoideka.vm_manager.requests.UserRequest;
import io.github.androoideka.vm_manager.responses.PermissionListResponse;
import io.github.androoideka.vm_manager.responses.UserResponse;

@Component
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponse UserToUserResponse(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setUserId(user.getUserId());
        userResponse.setEmail(user.getEmail());
        userResponse.setName(user.getName());
        userResponse.setSurname(user.getSurname());
        userResponse.setPermissionListResponse(new PermissionListResponse(user.getPermissions()));
        return userResponse;
    }

    public User UserRequestToUser(UserRequest userRequest) {
        User user = new User();
        user.setEmail(userRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setName(userRequest.getName());
        user.setSurname(userRequest.getSurname());
        user.setPermissions(userRequest.getPermissionList().toPermissionList());
        return user;
    }
}
