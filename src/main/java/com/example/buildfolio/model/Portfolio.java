package com.example.buildfolio.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
public class Portfolio {
    @Id
    private String id;
    private String username;
    @NotNull
    @Valid
    private PersonalDetails biodata;
    @Size(max = 5, message = "Experience must be <= 5")
    @Valid
    private List<Experience> experience;
    @Valid
    @Size(max = 5, message = "Project must be less <= 5")
    private List<Project> projects;
    @Valid
    @Size(min = 1, max = 20, message = "Skills must be between 1 to 20")
    private List<String> skills;
    @Valid
    @Size(min = 1, max = 5, message = "Education must be between 1 to 5")
    private List<Education> education;
    @Valid
    @Size(max = 5, message = "Achievements must be <= 5")
    private List<Achievement> achievements;
    private String templateId = "default";
}
