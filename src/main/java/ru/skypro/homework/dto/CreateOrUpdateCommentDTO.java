package ru.skypro.homework.dto;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
public class CreateOrUpdateCommentDTO {
    private String text;
}
