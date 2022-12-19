package com.example.Task.dto;

import lombok.EqualsAndHashCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
public class JwtResponseDTO {
    private final String type = "Bearer";
    private String accessToken;
}
