package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.dto.CommentsDTO;
import ru.skypro.homework.dto.CreateOrUpdateCommentDTO;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.exceptions.EntityNotFoundException;
import ru.skypro.homework.mapper.CommentMapper;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.CommentService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final AdRepository adRepository;
    private final UserRepository userRepository;

    public CommentsDTO getComments(Long id) {
        if (adRepository.existsById(id)) {
            List<CommentDTO> list = commentRepository.findCommentsByIdAd(id).stream()
                    .map(CommentMapper.INSTANCE::commentToCommentDTO)
                    .collect(Collectors.toList());

            CommentsDTO commentsDTO = new CommentsDTO();
            commentsDTO.setCount(commentRepository.findCommentsByIdAd(id).size());
            commentsDTO.setResults(list);

            return commentsDTO;
        } else {
            throw new EntityNotFoundException("Ad not found");
        }
    }

    public CommentDTO createComment(Long adId, CreateOrUpdateCommentDTO createOrUpdateCommentDTO, Authentication authentication) {
        if (adRepository.existsById(adId)) {

            LocalDateTime time = LocalDateTime.now();
            User user = userRepository.findByEmail(authentication.getName()).orElseThrow(RuntimeException::new);
            Ad ad = adRepository.findById(adId).orElse(null);

            Comment comment = CommentMapper.INSTANCE.createOrUpdateCommentDTOToComment(createOrUpdateCommentDTO);

            comment.setCreatedAt(time);
            comment.setUser(user);
            comment.setAd(ad);
            commentRepository.save(comment);

            return CommentMapper.INSTANCE.commentToCommentDTO(comment);
        } else {
            throw new EntityNotFoundException("Ad not found");
        }
    }

    public void removalComment(Long adId, Long commentId) {
        if (commentRepository.existsById(commentId)) {
            commentRepository.delete(getComment(commentId));
        } else {
            throw new EntityNotFoundException("Ad not found");
        }
    }

    public CommentDTO editComment(Long adId, Long commentId, CreateOrUpdateCommentDTO createOrUpdateCommentDTO) {
        if (commentRepository.existsById(commentId)) {
            Comment comment = getComment(commentId);
            comment.setText(createOrUpdateCommentDTO.getText());
            commentRepository.save(comment);
            return CommentMapper.INSTANCE.commentToCommentDTO(comment);
        } else {
            throw new EntityNotFoundException("Comment not found");
        }
    }

    public Comment getComment(Long pk) {
        return commentRepository.findById(pk).orElseThrow(RuntimeException::new);
    }
}
