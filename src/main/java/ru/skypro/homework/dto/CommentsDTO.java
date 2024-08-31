package ru.skypro.homework.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import ru.skypro.homework.entity.Comment;

import javax.persistence.OneToMany;
import java.util.List;

/**
 *Комментарии DTO
 */
@Data
public class CommentsDTO {
    /**
     *Общее количество комментариев
     */
    private Integer count;
    /**
     *Список комментариев
     */
    @OneToMany(mappedBy = "comment")
    @JsonManagedReference
    private List<Comment> results;
}
