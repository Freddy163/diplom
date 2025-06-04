package com.example.fishop.service;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProfileUpdateForm {
    @Email
    private String email;

    @Size(min = 6, message = "Пароль должен быть не менее 6 символов")
    private String newPassword;
}
