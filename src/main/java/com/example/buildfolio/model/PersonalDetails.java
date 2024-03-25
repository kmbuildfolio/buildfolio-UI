package com.example.buildfolio.model;

import lombok.Data;

@Data
public class PersonalDetails {
    @Data
    public static class Social{
        String key;
        String value;
    }
    private String name;
    private String email;
    private String phone;
    private Social[] socials;
    private String address;
    private String description;
}