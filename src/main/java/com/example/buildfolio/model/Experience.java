package com.example.buildfolio.model;

import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class Experience {
    @Size(min = 3, max = 25, message = "Company name must be between 3 to 25 characters")
    private String company;
    @Size(min = 3, max = 10, message = "Role must be between 3 to 10 characters")
    private String role;
    @Size(min = 3, max = 40, message = "Location must be between 3 to 40 characters")
    private String location;
    private LocalDate from;
    private LocalDate to;
    private String image;
    @Size(min = 20, max = 200, message = "Description must be between 20 and 200 characters")
    private String description;
}