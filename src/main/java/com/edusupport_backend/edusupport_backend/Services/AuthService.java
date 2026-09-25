package com.edusupport_backend.edusupport_backend.Services;


import com.edusupport_backend.edusupport_backend.DTO.LoginRequest;
import com.edusupport_backend.edusupport_backend.DTO.LoginResponse;
import com.edusupport_backend.edusupport_backend.DTO.RegisterRequest;

public interface AuthService {

    LoginResponse login(LoginRequest request);

    void register(RegisterRequest request);
}
