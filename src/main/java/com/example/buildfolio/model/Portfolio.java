package com.example.buildfolio.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
public class Portfolio {
    @Id
    private String id;
    private String username;
    private PersonalDetails biodata;
    private List<Experience> experience;
    private List<Project> projects;
    private List<String> skills;
    private List<Education> education;
    private List<Achievement> achievements;
    private String templateId = "default";
}
