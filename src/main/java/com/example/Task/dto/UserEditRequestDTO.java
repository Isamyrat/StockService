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
@AllArgsConstructor
public class UserEditRequestDTO {
    @NotBlank(message = "Full name can not be empty.")
    @Size(max = 100)
    private String fullName;

    @NotBlank(message = "Username can not be empty.")
    @Size(max = 50)
    private String username;
}
