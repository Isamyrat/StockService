package com.example.Task.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@Builder
public class UserDto {
    public static final String USERNAME_CAN_NOT_BE_EMPTY = "Username can not be empty.";
    public static final String PASSWORD_CAN_NOT_BE_EMPTY = "Password can not be empty.";

    private Integer id;

    @NotBlank(message = "Full name can not be empty.")
    @Size(max = 100)
    private String fullName;

    @NotBlank(message = USERNAME_CAN_NOT_BE_EMPTY)
    @Size(max = 50)
    private String username;

    @NotBlank(message = PASSWORD_CAN_NOT_BE_EMPTY)
    @Size(max = 40)
    private String password;


    public UserDto(String fullName, String username, String password) {
        this.fullName = fullName;
        this.username = username;
        this.password = password;
    }

    public UserDto(String fullName, String username) {
        this.fullName = fullName;
        this.username = username;
    }
}
