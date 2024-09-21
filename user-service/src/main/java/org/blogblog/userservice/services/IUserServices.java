package org.blogblog.userservice.services;

import org.blogblog.userservice.dto.request.SignInRequest;
import org.blogblog.userservice.dto.request.SignUpRequest;
import org.blogblog.userservice.dto.response.JwtResponse;
import org.blogblog.userservice.dto.response.RegisterResponse;

public interface IUserServices {
    RegisterResponse register(SignUpRequest request);
    JwtResponse signIn(SignInRequest request);
}
