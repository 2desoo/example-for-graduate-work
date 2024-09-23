package ru.skypro.homework.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.Email;

// Data Transfer Object (представляет собой сущность пользователя)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    @JsonProperty("id")
    private Integer id; // id пользователя

    @Email
    @JsonProperty("email")
    private String email; // почта пользователя

    @JsonProperty("first_name")
    private String firstName; // имя пользователя

    @JsonProperty("last_name")
    private String lastName; // фамилия пользователя

    @JsonProperty("phone")
    private String phone; // телефон пользователя

    @JsonProperty("role")
    private String role; // роль пользователя

    @JsonProperty("image")
    private String image; // изображение пользователя
}
