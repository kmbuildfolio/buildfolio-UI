package com.example.buildfolio.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PersonalDetails {
    @Data
    public static class Social{
        @Size(min = 2, max = 25, message = "Social value must be between 2 to 25 characters")
        String key;
        @Size(min = 5, max = 100, message = "Social key must be between 5 to 100 characters")
        String value;
    }
    @Size(min = 3, max = 25, message = "Name must be between 3 to 25 letters")
    private String name;
    @Email(message = "Must be an email")
    @Size(max = 50, message = "Email must be less than 50 characters")
    private String email;
    @Size(min = 10,max = 15,message = "Phone number must be 10 to 15 characters")
    private String phone;
    @Size(min = 1, max = 8, message = "Social must be between 1 to 8")
    private Social[] socials;
    @Size(min = 5, max = 50, message = "Location must be between 5 to 40 characters")
    private String address;
    @Size(min = 15, max = 70, message = "Introduction must be between 15 to 50 characters")
    private String introduction;
    @Size(min = 20, max = 200, message = "Description must be between 20 to 200 characters")
    private String description;
}