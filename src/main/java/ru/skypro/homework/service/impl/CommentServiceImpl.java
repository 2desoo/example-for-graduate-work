package ru.skypro.homework.service.impl;

import org.springframework.stereotype.Service;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.service.CommentService;

import java.util.Collection;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Collection<Comment> getComments() {
        return null;
    }

    public Comment createComment(Comment comment) {
        return null;
    }

    public Comment removalComment(Integer id) {
        return null;
    }

    public Comment editComment(Comment comment) {
        return null;
    }
}
