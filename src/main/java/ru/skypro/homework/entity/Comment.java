package ru.skypro.homework.entity;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

/**
 *Комментарий
 */
@Entity(name = "comment")
@Data
public class Comment {

    @Schema(description = "Id автора комментария")
    private Integer author;
    @Schema(description = "Ссылка на аватар автора комментария")
    private String authorImage;
    @Schema(description = "Имя создателя комментария")
    private String authorFirstName;
    @Schema(description = "Дата и время создания комментария в миллисекундах с 00:00:00 01.01.1970")
    private Integer createdAt;
    @Schema(description = "Id комментария")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pk;
    @Schema(description = "Текст комментария")
    private String text;
}
