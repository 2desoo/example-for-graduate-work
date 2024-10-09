package ru.skypro.homework.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.util.List;

// Сущность для представления пользователя в базе данных.
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"ads", "comments"})
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // id пользователя

    private String email; // адрес электронной почты пользователя
    private String password; // пароль пользователя
    private String firstName; // имя пользователя
    private String lastName; // фамилия пользователя
    private String phone; // номер телефона пользователя

    @Enumerated(EnumType.STRING) //используется для хранения значений типа enum в базе данных в виде строк.
    private Role role; // роль пользователя

    @OneToOne
    @JsonBackReference
    @JoinColumn(name = "image_id")
    private Image image; // аватар пользователя

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Ad> ads; // список объявлений пользователя

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Comment> comments; // список комментариев пользователя
}
