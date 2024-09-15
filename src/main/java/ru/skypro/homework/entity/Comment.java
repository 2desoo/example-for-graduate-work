package ru.skypro.homework.entity;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comment")
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class Comment {

    /**
     * Id комментария
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long pk;
    /**
     * Дата и время создания комментария в миллисекундах с 00:00:00 01.01.1970
     */
    LocalDateTime createdAt;
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
    /**
     * Связь с объявлениям
     */
    @ManyToOne
    @JoinColumn(name = "ad_pk", nullable = false)
    Ad ad;
}
