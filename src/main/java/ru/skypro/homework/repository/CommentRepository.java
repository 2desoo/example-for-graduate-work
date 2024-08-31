package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.homework.entity.Comment;

/**
 * Пока просто для понятия логики
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {
}