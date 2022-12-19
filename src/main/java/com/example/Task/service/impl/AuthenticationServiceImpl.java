package com.example.Task.service.impl;

import com.example.Task.dto.JwtRequestDTO;
import com.example.Task.dto.JwtResponseDTO;
import com.example.Task.exception.BadRequestException;
import com.example.Task.jwt.JwtUtils;
import com.example.Task.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.security.core.AuthenticationException;


@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    public static final String USER_NAME_NOT_BE_NULL = "Username should not be null or blank.";
    public static final String PASSWORD_NOT_BE_NULL = "Password should not be null or blank.";
    public static final String USER_NOT_FOUND = "User not found.";

    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    public JwtResponseDTO login(JwtRequestDTO jwtRequest) {
        validateLoginRequest(jwtRequest);
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword()));
        } catch (AuthenticationException e) {
            throw new BadRequestException(USER_NOT_FOUND, e);
        }
        JwtResponseDTO jwtResponseDTO = new JwtResponseDTO(jwtUtils.getJwtToken(jwtRequest.getUsername()));
        System.out.println(jwtResponseDTO.getAccessToken());

        return new JwtResponseDTO(jwtUtils.getJwtToken(jwtRequest.getUsername()));
    }

    private void validateLoginRequest(JwtRequestDTO jwtRequest) {
        if (jwtRequest.getUsername() == null || jwtRequest.getUsername().isBlank()) {
            throw new BadRequestException(USER_NAME_NOT_BE_NULL);
        }
        if (jwtRequest.getPassword() == null || jwtRequest.getPassword().isBlank()) {
            throw new BadRequestException(PASSWORD_NOT_BE_NULL);
        }
    }
}
