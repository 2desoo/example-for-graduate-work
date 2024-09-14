package ru.skypro.homework.service;

import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.dto.CommentsDTO;
import ru.skypro.homework.dto.CreateOrUpdateCommentDTO;
import ru.skypro.homework.entity.Comment;

import java.util.Collection;
import java.util.List;

public interface CommentService {
    /**
     * Пока просто для понятия логики
     */
    CommentsDTO getComments(Integer id);
    /**
     * Пока просто для понятия логики
     */
    CommentDTO createComment(Integer adId, CreateOrUpdateCommentDTO comment);
    /**
     * Пока просто для понятия логики
     */
    Comment removalComment(Integer adId, Integer commentId);
    /**
     * Пока просто для понятия логики
     */
    Comment editComment(Comment comment);
}
