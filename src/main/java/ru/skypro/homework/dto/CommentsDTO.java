package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.skypro.homework.entity.Comment;

import java.util.List;

/**
 *Комментарии DTO
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentsDTO {
    @Schema(description = "Общее количество комментариев")
    Integer count;
    @Schema(description = "Список комментариев")
    List<Comment> results;
}
