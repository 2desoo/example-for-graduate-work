package ru.skypro.homework.entity;


import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDateTime;

// Сущность комментария для объявления
@Entity
@Table(name = "comment")
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long pk; // id комментария

    LocalDateTime createdAt; // время создания комментария
    String text; // текст комментария

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    User user; // создатель комментария

    @ManyToOne
    @JoinColumn(name = "ad_pk", nullable = false)
    Ad ad; // объявление, к которому относится комментарий
}
