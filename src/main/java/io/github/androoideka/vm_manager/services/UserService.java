package io.github.androoideka.vm_manager.services;

import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetailsService;

import io.github.androoideka.vm_manager.model.User;
import io.github.androoideka.vm_manager.requests.UserRequest;
import io.github.androoideka.vm_manager.responses.UserResponse;

public interface UserService extends UserDetailsService {
    UserResponse findByEmail(String email);

    void create(UserRequest userRequest);

    Page<UserResponse> list(Integer page, Integer size);

    UserResponse view(Long userId);

    void edit(UserRequest userRequest);

    void delete(Long id);

    User getCurrentUser();
}
