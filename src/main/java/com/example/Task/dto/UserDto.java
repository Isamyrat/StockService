package com.example.Task.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class UserDto {
    public static final String USERNAME_CAN_NOT_BE_EMPTY = "Username can not be empty.";
    public static final String PASSWORD_CAN_NOT_BE_EMPTY = "Password can not be empty.";
    public static final String FULL_NAME_CAN_NOT_BE_EMPTY = "Full name can not be empty.";

    private Integer id;

    @NotBlank(message = FULL_NAME_CAN_NOT_BE_EMPTY)
    @Size(max = 100)
    private String fullName;

    @NotBlank(message = USERNAME_CAN_NOT_BE_EMPTY)
    @Size(max = 50)
    private String username;

    @NotBlank(message = PASSWORD_CAN_NOT_BE_EMPTY)
    @Size(max = 40)
    private String password;
}
