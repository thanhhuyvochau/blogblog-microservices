package org.blogblog.userservice.controllers;

import jakarta.validation.Valid;
import org.blogblog.userservice.dto.request.RegisterRequest;
import org.blogblog.userservice.dto.response.RegisterResponse;
import org.blogblog.userservice.entities.common.ApiResponse;
import org.blogblog.userservice.services.IUserServices;
import org.blogblog.userservice.services.concretes.UserServicesImpl;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final IUserServices userServices;

    public AuthController(IUserServices userServices) {
        this.userServices = userServices;
    }

    @PostMapping("/register")
    public ApiResponse<RegisterResponse> register(@RequestBody @Valid RegisterRequest request) {
        return ApiResponse.success(userServices.register(request));
    }
}
