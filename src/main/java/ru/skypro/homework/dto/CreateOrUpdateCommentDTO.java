package ru.skypro.homework.dto;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *Создать или обновить комментарий DTO
 */
@Data
public class CreateOrUpdateCommentDTO {
    /**
     *Текст комментария
     */
    private String text;
}
