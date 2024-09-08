package ru.skypro.homework.service;

import ru.skypro.homework.dto.CreateOrUpdateCommentDTO;
import ru.skypro.homework.entity.Comment;

import java.util.Collection;

public interface CommentService {
    /**
     * Пока просто для понятия логики
     */
    Collection<Comment> getComments();
    /**
     * Пока просто для понятия логики
     */
    Comment createComment(Integer adId, CreateOrUpdateCommentDTO comment);
    /**
     * Пока просто для понятия логики
     */
    Comment removalComment(Integer id);
    /**
     * Пока просто для понятия логики
     */
    Comment editComment(Comment comment);
}
