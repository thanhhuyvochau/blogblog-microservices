package org.blogblog.userservice.services;

import org.blogblog.userservice.dto.request.RegisterRequest;
import org.blogblog.userservice.dto.response.RegisterResponse;

public interface IUserServices {
    RegisterResponse register(RegisterRequest request);
}
