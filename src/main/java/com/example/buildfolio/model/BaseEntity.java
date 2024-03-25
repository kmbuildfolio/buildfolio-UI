package com.example.buildfolio.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BaseEntity {
    @JsonIgnore
    public LocalDateTime createdAt;

    @JsonIgnore
    public String createdBy;

    @JsonIgnore
    public LocalDateTime updatedAt;

    @JsonIgnore
    public String updatedBy;
}
