package ru.skypro.homework.dto;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *Комментарий DTO
 */
@Data
public class CommentDTO {

    /**
     *Id автора комментария
     */
    private Integer author;
    /**
     *Ссылка на аватар автора комментария
     */
    private String authorImage;
    /**
     *Имя создателя комментария
     */
    private String authorFirstName;
    /**
     *Дата и время создания комментария в миллисекундах с 00:00:00 01.01.1970'
     */
    private Integer createdAt;
    /**
     *Id комментария
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pk;
    /**
     *Текст комментария
     */
    private String text;
}
