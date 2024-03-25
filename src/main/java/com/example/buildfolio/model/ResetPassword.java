package com.example.buildfolio.model;

import lombok.Data;

@Data
public class ResetPassword {
    private String otp;
    private String email;
    private String newPass;
    private String confirmPass;
}
