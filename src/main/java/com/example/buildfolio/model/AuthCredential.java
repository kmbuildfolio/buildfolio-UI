package com.example.buildfolio.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthCredential {
    private String userNameOrEmail;
    @JsonIgnore
    private String email;
    private String userName;
    private String pass;
    private String role;


    public AuthCredential(String email, String userName, String pass, String role){
        this.email = email;
        this.userName = userName;
        this.pass = pass;
        this.role = role;
    }
}