package com.example.Task.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.example.Task.dto.UserDto.FULL_NAME_CAN_NOT_BE_EMPTY;
import static com.example.Task.dto.UserDto.USERNAME_CAN_NOT_BE_EMPTY;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class UserEditRequestDTO {
    @NotBlank(message =  FULL_NAME_CAN_NOT_BE_EMPTY)
    @Size(max = 100)
    private String fullName;

    @NotBlank(message = USERNAME_CAN_NOT_BE_EMPTY)
    @Size(max = 50)
    private String username;
}
