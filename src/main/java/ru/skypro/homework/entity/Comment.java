package ru.skypro.homework.entity;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;


import javax.persistence.*;
import java.util.Objects;

/**
 *Комментарий
 */
@Entity(name = "comment")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Comment {

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @Schema(description = "Ссылка на аватар  комментария")
    private String authorImage;
    @Schema(description = "Дата и время создания комментария в миллисекундах с 00:00:00 01.01.1970")
    private Integer createdAt;
    @Schema(description = "Id комментария")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pk;
    @Schema(description = "Текст комментария")
    private String text;
}
