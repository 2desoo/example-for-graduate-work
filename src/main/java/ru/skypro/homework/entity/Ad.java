package ru.skypro.homework.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

// Сущность объявления
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"user", "comments"})
@Table(name = "ads")
public class Ad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pk; // id объявления

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Image image; // изображение

    private Integer price; // цена
    private String title; // название
    private String description; // описание

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // создатель объявления

    @OneToMany(mappedBy = "ad", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Comment> comments; // комментарии
}
