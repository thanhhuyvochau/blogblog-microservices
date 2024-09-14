package org.blogblog.userservice.services.concretes;

import jakarta.transaction.Transactional;
import org.blogblog.userservice.dto.request.RegisterRequest;
import org.blogblog.userservice.dto.response.RegisterResponse;
import org.blogblog.userservice.entities.User;
import org.blogblog.userservice.repositories.UserRepository;
import org.blogblog.userservice.services.IUserServices;
import org.blogblog.userservice.utils.ObjectUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserServicesImpl implements IUserServices {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServicesImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public RegisterResponse register(RegisterRequest request) {
        User registeredUser = User.builder().email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .password(this.passwordEncoder.encode(request.getPassword())).build();
        User savedUser = this.userRepository.save(registeredUser);
        return ObjectUtil.copyProperties(savedUser, new RegisterResponse(), RegisterResponse.class, true);
    }
}
