package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.CreateOrUpdateCommentDTO;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.service.CommentService;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    public Collection<Comment> getComments() {
        return null;
    }

    public Comment createComment(Integer adId, CreateOrUpdateCommentDTO comment) {
        return null;
    }

    public Comment removalComment(Integer id) {
        return null;
    }

    public Comment editComment(Comment comment) {
        return null;
    }
}
