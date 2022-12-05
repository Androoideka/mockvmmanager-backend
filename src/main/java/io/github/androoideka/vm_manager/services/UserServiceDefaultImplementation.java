package io.github.androoideka.vm_manager.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import io.github.androoideka.vm_manager.mappers.UserMapper;
import io.github.androoideka.vm_manager.model.User;
import io.github.androoideka.vm_manager.repositories.UserRepository;
import io.github.androoideka.vm_manager.requests.UserRequest;
import io.github.androoideka.vm_manager.responses.UserResponse;

public class UserServiceDefaultImplementation implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceDefaultImplementation(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = this.userRepository
                .findByUsername(email)
                .orElseThrow(() -> new UsernameNotFoundException("User with email " + email + " could not be found."));
        return user;
    }

    @Override
    public UserResponse findByEmail(String email) {
        User user = this.userRepository
                .findByUsername(email)
                .orElseThrow(() -> new UsernameNotFoundException("User with email " + email + " could not be found."));
        return this.userMapper.UserToUserResponse(user);
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
                .orElseThrow(
                        () -> new UsernameNotFoundException("Cannot save changes because the user does not exist."));
        User newUser = this.userMapper.UserRequestToUser(userRequest);
        newUser.setUserId(userRequest.getUserId());
        newUser.setPassword(user.getPassword());
        this.userRepository.save(newUser);
    }

    @Override
    public void delete(Long id) {
        User user = this.userRepository
                .findById(id)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Cannot complete the delete operation because the user does not exist."));
        this.userRepository.delete(user);
    }

    @Override
    public User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return this.userRepository.findByUsername(email)
                .orElseThrow(() -> new UsernameNotFoundException("You are not properly authenticated."));
    }
}
