package com.example.Task.controller;


import com.example.Task.dto.JwtRequestDTO;
import com.example.Task.dto.JwtResponseDTO;
import com.example.Task.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.example.Task.jwt.JwtUtils.BEARER_PREFIX;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Controller
@RequestMapping(AuthenticationController.AUTH_URL)
@RequiredArgsConstructor
public class AuthenticationController {
    public static final String AUTH_URL = "/auth";

    private final AuthenticationService authenticationService;

    @GetMapping("/login")
    public String registration(Model model) {
        JwtRequestDTO jwtRequestDTO = new JwtRequestDTO();
        model.addAttribute("jwtRequestDTO", jwtRequestDTO);
        return "login";
    }

/*
    @PostMapping(value = "/authenticate")
    public ResponseEntity<String> authenticate(
        @Valid JwtRequestDTO jwtRequestDTO,
        HttpServletResponse httpServletResponse) {

        JwtResponseDTO jwtResponseDTO = authenticationService.login(jwtRequestDTO);
  *//*
        httpServletResponse.setHeader(AUTHORIZATION, BEARER_PREFIX + jwtResponseDTO.getAccessToken());*//*

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set(AUTHORIZATION,
                            BEARER_PREFIX + jwtResponseDTO.getAccessToken());
        responseHeaders.add("Location", "/main");
        return new ResponseEntity<>(responseHeaders, HttpStatus.FOUND);

    }*/


   //  for test in postman to get token

    @PostMapping("/authenticate")
    public ResponseEntity<JwtResponseDTO> authenticate(
        @RequestBody JwtRequestDTO jwtRequestDTO) {

        return ResponseEntity.ok(authenticationService.login(jwtRequestDTO));
    }
}
