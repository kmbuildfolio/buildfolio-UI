package com.example.buildfolio.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Experience {
    private String company;
    private String role;
    private String location;
    private LocalDate from;
    private LocalDate to;
    private String image;
    private String description;
}