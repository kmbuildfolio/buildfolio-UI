package com.example.buildfolio.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Education {
    private String institution;
    private String university;
    private String name;
    private String course;
    private String location;
    private LocalDate to;
    private LocalDate from;
}