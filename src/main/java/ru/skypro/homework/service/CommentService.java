package ru.skypro.homework.service;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
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
    CommentsDTO getComments(Long id);
    /**
     * Пока просто для понятия логики
     */
    CommentDTO createComment(Long adId, CreateOrUpdateCommentDTO comment);
    /**
     * Пока просто для понятия логики
     */
    void removalComment(Long adId, Long commentId);

    /**
     * Пока просто для понятия логики
     */
    CommentDTO editComment(Long adId, Long commentId, CreateOrUpdateCommentDTO createOrUpdateCommentDTO);
}
