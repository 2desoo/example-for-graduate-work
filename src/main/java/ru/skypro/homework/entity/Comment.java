package ru.skypro.homework.entity;


import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity(name = "comment")
@NoArgsConstructor
@Data
public class Comment {
    /**
     *id автора комментария
     */
    private Integer author;
    private String authorImage;
    private String authorFirstName;
    private Integer createdAt;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pk;
    private String text;
}
