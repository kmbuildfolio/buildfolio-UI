package com.example.buildfolio.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "person")
public class Person extends BaseEntity{

    @Id
    private String id;

    @NotBlank(message = "Email must not be blank")
    @Email(message = "Please provide a valid email")
    @Indexed(unique = true)
    private String email;

    @NotBlank(message = "UserName Must Not Be Blank")
    @Indexed(unique = true)
    private String userName;

    @NotBlank(message = "Password must not be blank")
    @Size(min = 5, message = "Password must be in 5 digit")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
}