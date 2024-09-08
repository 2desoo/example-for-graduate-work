package ru.skypro.homework.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "ad")
public class Ad {

    Integer author;
    @OneToOne
    @JsonBackReference
    Image image;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer pk;
    Integer price;
    String title;
    String description;
    @ManyToOne
    User user;
    @OneToMany
    List<Comment> comments;
}
