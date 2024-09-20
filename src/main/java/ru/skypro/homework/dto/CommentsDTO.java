package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

// Data Transfer Object для списка комментариев
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentsDTO {
    @Schema(description = "Общее количество комментариев")
    Integer count;
    @Schema(description = "Список комментариев")
    List<CommentDTO> results;
}
