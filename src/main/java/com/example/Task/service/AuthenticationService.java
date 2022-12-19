package com.example.Task.service;

import com.example.Task.dto.JwtRequestDTO;
import com.example.Task.dto.JwtResponseDTO;

public interface AuthenticationService {
    JwtResponseDTO login(JwtRequestDTO jwtRequest);
}
