package ru.skypro.homework.entity;


import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Entity
@Table(name = "comment")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class Comment {

    /**
     * Id комментария
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer pk;
    /**
     * Ссылка на аватар  комментария
     */
    String authorImage;
    /**
     * Дата и время создания комментария в миллисекундах с 00:00:00 01.01.1970
     */
    Integer createdAt;
    /**
     * Текст комментария
     */
    String text;
    /**
     * Связь с User
     */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    User user;
}
