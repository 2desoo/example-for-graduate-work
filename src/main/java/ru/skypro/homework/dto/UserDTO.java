package ru.skypro.homework.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Email;

// Data Transfer Object (представляет собой сущность пользователя)
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDTO {

    @JsonProperty("id")
    Integer id; // id пользователя

    @Email
    @JsonProperty("email")
    String email; // почта пользователя

    @JsonProperty("first_name")
    String firstName; // имя пользователя

    @JsonProperty("last_name")
    String lastName; // фамилия пользователя

    @JsonProperty("phone")
    String phone; // телефон пользователя

    @JsonProperty("role")
    String role; // роль пользователя

    @JsonProperty("image")
    String image; // изображение пользователя
}
