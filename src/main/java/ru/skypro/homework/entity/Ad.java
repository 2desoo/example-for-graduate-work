package ru.skypro.homework.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.List;

// Сущность объявления
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "ad")
public class Ad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long pk; // id объявления
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    Image image; // изображение
    Integer price; // цена
    String title; // название
    String description; // описание
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    User user; // создатель объявления
    @OneToMany
    List<Comment> comments; // комментарии
}
