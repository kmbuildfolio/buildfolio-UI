package com.example.buildfolio.model;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class Project {
    @Size(min = 3, max = 25, message = "Project name must be between 3 to 25 characters")
    private String name;
    @Size(min = 5, max = 50, message = "Tech must be between 5 to 50 characters")
    private String tech;
    private String url;
    private String image;
    private String code;
    @Size(min = 20, max = 200, message = "Description must be between 20 to 200 characters")
    private String description;
}
