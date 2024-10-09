package ru.skypro.homework.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

// Сущность комментария для объявления
@Entity
@Table(name = "comments")
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"user", "ad"})
@Data
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pk; // id комментария

    private LocalDateTime createdAt; // время создания комментария
    private String text; // текст комментария

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // создатель комментария

    @ManyToOne
    @JoinColumn(name = "ad_pk", nullable = false)
    private Ad ad; // объявление, к которому относится комментарий
}
