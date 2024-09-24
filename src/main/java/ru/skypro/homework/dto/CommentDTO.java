package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

// Data Transfer Object для комментария
@Data
public class CommentDTO {

    @Schema(description = "Id автора комментария")
    private Long author;

    @Schema(description = "Ссылка на аватар комментария")
    private String authorImage;

    @Schema(description = "Имя создателя комментария")
    private String authorFirstName;

    @Schema(description = "Дата и время создания комментария в миллисекундах с 00:00:00 01.01.1970")
    private LocalDateTime createdAt;

    @Schema(description = "Id комментария")
    private Long pk;

    @Schema(description = "Текст комментария")
    private String text;
}
