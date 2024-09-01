package ru.skypro.homework.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;


import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentDTO {

    @Schema(description = "Id автора комментария")
    Integer author;
    @Schema(description = "Ссылка на аватар автора комментария")
    String authorImage;
    @Schema(description = "Имя создателя комментария")
    String authorFirstName;
    @Schema(description = "Дата и время создания комментария в миллисекундах с 00:00:00 01.01.1970")
    Integer createdAt;
    @Schema(description = "Id комментария")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer pk;
    @Schema(description = "Текст комментария")
    String text;
}
