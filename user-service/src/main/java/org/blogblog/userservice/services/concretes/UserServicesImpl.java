package org.blogblog.userservice.services.concretes;

import jakarta.transaction.Transactional;
import org.blogblog.userservice.config.security.jwt.JwtUtil;
import org.blogblog.userservice.dto.request.SignInRequest;
import org.blogblog.userservice.dto.request.SignUpRequest;
import org.blogblog.userservice.dto.response.JwtResponse;
import org.blogblog.userservice.dto.response.RegisterResponse;
import org.blogblog.userservice.entities.User;
import org.blogblog.userservice.entities.common.ApiException;
import org.blogblog.userservice.repositories.UserRepository;
import org.blogblog.userservice.services.IUserServices;
import org.blogblog.userservice.utils.ObjectUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServicesImpl implements IUserServices {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final UserDetailsService userDetailsService;

    private final JwtUtil jwtUtil;

    public UserServicesImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, UserDetailsService userDetailsService, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public RegisterResponse register(SignUpRequest request) {
        User registeredUser = User.builder().email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .password(this.passwordEncoder.encode(request.getPassword())).build();
        User savedUser = this.userRepository.save(registeredUser);
        return ObjectUtil.copyProperties(savedUser, new RegisterResponse(), RegisterResponse.class, true);
    }

    @Override
    public JwtResponse signIn(SignInRequest request) {
        try {
            User user = (User) userDetailsService.loadUserByUsername(request.getEmail());
            if (!user.isAccountNonLocked()) {
                throw new DisabledException("Account is banned");
            } else if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                throw new AuthenticationCredentialsNotFoundException("Wrong username or password");
            }
            String jwtToken = jwtUtil.generateToken(user, false);
            JwtResponse signInResponse = new JwtResponse();
            signInResponse.setToken(jwtToken);
            signInResponse.setEmail(user.getEmail());
            signInResponse.setFirstName(user.getFirstName());
            signInResponse.setPermissions(user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
            signInResponse.setLastName(user.getLastName());
            return signInResponse;
        } catch (DisabledException e) {
            throw ApiException.create(HttpStatus.FORBIDDEN).withMessage("Account is banned");
        } catch (AuthenticationException e) {
            throw ApiException.create(HttpStatus.FORBIDDEN).withMessage("Wrong username or password");
        }
    }
}
