package ru.skypro.homework.entity;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;


import javax.persistence.*;
import java.util.Objects;

@Entity(name = "comment")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class Comment {

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @Schema(description = "Ссылка на аватар  комментария")
    private String authorImage;
    @Schema(description = "Дата и время создания комментария в миллисекундах с 00:00:00 01.01.1970")
    Integer createdAt;
    @Schema(description = "Id комментария")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer pk;
    @Schema(description = "Текст комментария")
    String text;
}
