package org.blogblog.userservice.controllers;

import jakarta.validation.Valid;
import org.blogblog.userservice.dto.request.SignInRequest;
import org.blogblog.userservice.dto.request.SignUpRequest;
import org.blogblog.userservice.dto.response.JwtResponse;
import org.blogblog.userservice.dto.response.RegisterResponse;
import org.blogblog.userservice.entities.common.ApiResponse;
import org.blogblog.userservice.services.IUserServices;
import org.springframework.http.ResponseEntity;
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
    public ApiResponse<RegisterResponse> register(@RequestBody @Valid SignUpRequest request) {
        return ApiResponse.success(userServices.register(request));
    }

    @PostMapping("/signin")
    public ResponseEntity<ApiResponse<JwtResponse>> login(@RequestBody @Valid SignInRequest request) {
        return ResponseEntity.ok(ApiResponse.success(userServices.signIn(request)));
    }
}
