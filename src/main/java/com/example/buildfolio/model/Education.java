package com.example.buildfolio.model;

import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class Education {
    @Size(min = 5, max = 40, message = "Institution must be between 5 to 40 characters")
    private String institution;
    @Size(min = 5, max = 40, message = "University must be between 5 to 40 characters")
    private String university;
    @Size(min = 3, max = 40, message = "Course must be between 3 to 40 characters")
    private String course;
    @Size(min = 3, max = 40, message = "Location must be between 3 to 40 characters")
    private String location;
    private LocalDate to;
    private LocalDate from;
}