package ru.skypro.homework.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.skypro.homework.entity.Comment;

import javax.persistence.OneToMany;
import java.util.List;

/**
 *Комментарии DTO
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentsDTO {
    @Schema(description = "Общее количество комментариев")
    private Integer count;
    private List<Comment> results;
}
