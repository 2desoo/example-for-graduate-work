package ru.skypro.homework.entity;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;

// Сущность изображения для хранения в базе данных.
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "images")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // id изображения

    private long fileSize; // размер изображения
    private String filePath; // путь к изображению
    private String mediaType; // тип изображения

    @Lob
    @Type(type = "org.hibernate.type.ImageType")
    private byte[] data; // данные изображения
}
