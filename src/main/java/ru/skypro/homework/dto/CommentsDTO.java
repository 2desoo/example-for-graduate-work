package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

// Data Transfer Object для списка комментариев
@Data
public class CommentsDTO {
    @Schema(description = "Общее количество комментариев")
    private Integer count;

    @Schema(description = "Список комментариев")
    private List<CommentDTO> results;
}
