package ru.skypro.homework.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Email;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDTO {

    @JsonProperty("id")
    Integer id;

    @Email
    @JsonProperty("email")
    String email;

    @JsonProperty("first_name")
    String firstName;

    @JsonProperty("last_name")
    String lastName;

    @JsonProperty("phone")
    String phone;

    @JsonProperty("role")
    String role;

    @JsonProperty("image")
    String image;
}
