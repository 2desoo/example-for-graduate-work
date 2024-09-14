package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.dto.CommentsDTO;
import ru.skypro.homework.dto.CreateOrUpdateCommentDTO;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.mapper.CommentMapper;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.CommentService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final AdRepository adRepository;
    private final UserRepository userRepository;

    public CommentsDTO getComments(Integer id) {
        List<CommentDTO> list = commentRepository.findByIdAdOrComments(id).stream()
                .map(CommentMapper.INSTANCE::commentToCommentDTO)
                .collect(Collectors.toList());

        CommentsDTO commentsDTO = new CommentsDTO();
        commentsDTO.setCount(commentRepository.findByIdAdOrComments(id).size());
        commentsDTO.setResults(list);
        return commentsDTO;
    }

    public CommentDTO createComment(Integer adId, CreateOrUpdateCommentDTO createOrUpdateCommentDTO) {
        Ad ad = adRepository.findById(adId.longValue()).orElse(null);
        return null;
    }

    public Comment removalComment(Integer adId, Integer commentId) {
        return null;
    }

    public Comment editComment(Comment comment) {
        return null;
    }
}
