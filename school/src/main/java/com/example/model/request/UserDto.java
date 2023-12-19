package com.example.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @NotBlank
    @Size(min = 9, max = 9)
    private String phoneNumber;

    @NotBlank
    @Size(min = 6)
    private String password;
}
