package com.example.buildfolio.model;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class Achievement {
    @Size(max = 25, message = "Achievements name must be <= 25 characters")
    private String name;
    @Size(max = 200, message = "Description must be <= 200 characters")
    private String description;
}