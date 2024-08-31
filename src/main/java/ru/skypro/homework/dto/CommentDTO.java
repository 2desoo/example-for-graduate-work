package ru.skypro.homework.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentDTO {
    Integer author;
    String authorImage;
    String authorFirstName;
    Integer createdAt;
    Integer pk;
    String text;
}
