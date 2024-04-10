package com.example.buildfolio.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ResetPassword {
    @Size(min = 4, max = 8, message = "OTP must be between 4 to 10 characters")
    private String otp;
    @Email(message = "Must be an email")
    @Size(max = 40, message = "Email must be <= 40 characters")
    private String email;
    @Size(min = 5, max = 25, message = "Password must be between 3 to 25 characters")
    private String newPass;
    @Size(min = 5, max = 25, message = "Password must be between 3 to 25 characters")
    private String confirmPass;
}
