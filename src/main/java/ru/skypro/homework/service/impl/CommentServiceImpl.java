package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
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

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final AdRepository adRepository;
    private final UserRepository userRepository;

    public CommentsDTO getComments(Long id) {
        List<CommentDTO> list = commentRepository.findCommentsById(id).stream()
                .map(CommentMapper.INSTANCE::commentToCommentDTO)
                .collect(Collectors.toList());

        CommentsDTO commentsDTO = new CommentsDTO();
        commentsDTO.setCount(commentRepository.findCommentsById(id).size());
        commentsDTO.setResults(list);
        return commentsDTO;
    }

    public CommentDTO createComment(Long adId, CreateOrUpdateCommentDTO createOrUpdateCommentDTO) {
        LocalDateTime time = LocalDateTime.now();
        User user = userRepository.findUserById(adId);
        Ad ad = adRepository.findById(adId).orElse(null);
        Comment comment = CommentMapper.INSTANCE.createOrUpdateCommentDTOToComment(createOrUpdateCommentDTO);
        comment.setCreatedAt(time);
        comment.setUser(user);
        comment.setAd(ad);
        commentRepository.save(comment);
        return CommentMapper.INSTANCE.commentToCommentDTO(comment);
    }

    public void removalComment(Long adId, Long commentId) {
        Ad ad = adRepository.findAdById(adId);

        Comment comment = commentRepository.findById(commentId).orElseThrow(RuntimeException::new);
        commentRepository.delete(comment);
    }

    public CommentDTO editComment(Long adId, Long commentId, CreateOrUpdateCommentDTO createOrUpdateCommentDTO) {
        Comment comment = getComment(commentId);
        comment.setText(createOrUpdateCommentDTO.getText());
        commentRepository.save(comment);
        return CommentMapper.INSTANCE.commentToCommentDTO(comment);
    }

    public Comment getComment(Long pk) {
        return commentRepository.findById(pk).orElseThrow(RuntimeException::new);
    }

    /*public boolean adsExist(Long adId) {
        Ad ad = adRepository.findAdById()
    }*/
}
