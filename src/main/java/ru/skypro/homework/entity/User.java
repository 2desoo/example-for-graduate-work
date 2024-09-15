package ru.skypro.homework.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(unique = true, nullable = false)
    String email;

    @Column(nullable = false)
    String password;

    @Column(nullable = false)
    String firstName;

    @Column(nullable = false)
    String lastName;

    @Column(nullable = false)
    String phone;

    @Enumerated(EnumType.STRING) //используется для хранения значений типа enum в базе данных в виде строк.
    @Column(nullable = false)
    Role role;

    @OneToOne
    @JsonBackReference
    @JoinColumn(name = "image_id")
    Image image;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Ad> ads;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Comment> comments;
}
