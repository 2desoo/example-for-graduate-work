package ru.skypro.homework.entity;

import lombok.*;
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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long pk;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    Image image;
    Integer price;
    String title;
    String description;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    User user;
    @OneToMany
    List<Comment> comments;
}
