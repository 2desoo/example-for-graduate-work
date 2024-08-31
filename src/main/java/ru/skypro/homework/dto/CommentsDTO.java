package ru.skypro.homework.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import ru.skypro.homework.entity.Comment;

import javax.persistence.OneToMany;
import java.util.List;

@Data
public class CommentsDTO {
    private Integer count;
    @OneToMany(mappedBy = "comment")
    @JsonManagedReference
    private List<Comment> results;
}
