package ru.skypro.homework.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.List;

// Сущность для представления пользователя в базе данных.
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"user", "comments"})
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id; // id пользователя

    @Column(unique = true, nullable = false)
    String email; // адрес электронной почты пользователя

    @Column(nullable = false)
    String password; // пароль пользователя

    @Column(nullable = false)
    String firstName; // имя пользователя

    @Column(nullable = false)
    String lastName; // фамилия пользователя

    @Column(nullable = false)
    String phone; // номер телефона пользователя

    @Enumerated(EnumType.STRING) //используется для хранения значений типа enum в базе данных в виде строк.
    @Column(nullable = false)
    Role role; // роль пользователя

    @OneToOne
    @JsonBackReference
    @JoinColumn(name = "image_id")
    Image image; // аватар пользователя

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Ad> ads; // список объявлений пользователя

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Comment> comments; // список комментариев пользователя
}
