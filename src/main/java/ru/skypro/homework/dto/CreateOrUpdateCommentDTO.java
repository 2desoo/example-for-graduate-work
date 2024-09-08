package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;

/**
 *Создать или обновить комментарий DTO
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateOrUpdateCommentDTO {
    @Schema(description = "Текст комментария", minLength = 8, maxLength = 64)
    @Length(min = 8, max = 64)
    private String text;
}
