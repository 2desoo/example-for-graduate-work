package ru.skypro.homework.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;


import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentDTO {

    @Schema(description = "Id автора комментария")
    Long author;
    @Schema(description = "Ссылка на аватар комментария")
    String authorImage;
    @Schema(description = "Имя создателя комментария")
    String authorFirstName;
    @Schema(description = "Дата и время создания комментария в миллисекундах с 00:00:00 01.01.1970")
    LocalDateTime createdAt;
    @Schema(description = "Id комментария")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long pk;
    @Schema(description = "Текст комментария")
    String text;
}
