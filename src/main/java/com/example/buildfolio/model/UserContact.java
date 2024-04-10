package com.example.buildfolio.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserContact {
    @Size(max = 25, message = "Name length must be <= 25")
    private String name;
    @Email(message = "Must be an email")
    @Size(max = 30, message = "Email Length must be <= 30")
    private String email;
    @Size(min = 5, max = 200, message = "Message Length must be between 5 to 200")
    private String message;
}
