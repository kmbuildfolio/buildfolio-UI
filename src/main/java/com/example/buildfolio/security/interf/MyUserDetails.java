package com.example.buildfolio.security.interf;

import org.springframework.security.core.userdetails.UserDetails;

public interface MyUserDetails extends UserDetails {
    String getEmail();

    String getRole();
}
