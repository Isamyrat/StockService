package com.example.Task.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.example.Task.dto.UserDto.PASSWORD_CAN_NOT_BE_EMPTY;
import static com.example.Task.dto.UserDto.USERNAME_CAN_NOT_BE_EMPTY;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class JwtRequestDTO {
    @NotBlank(message = USERNAME_CAN_NOT_BE_EMPTY)
    private String username;
    @NotBlank(message = PASSWORD_CAN_NOT_BE_EMPTY)
    private String password;
}
