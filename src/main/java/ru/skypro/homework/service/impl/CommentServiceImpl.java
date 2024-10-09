package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.dto.CommentsDTO;
import ru.skypro.homework.dto.CreateOrUpdateCommentDTO;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.exception.EntityNotFoundException;
import ru.skypro.homework.exception.ForbiddenException;
import ru.skypro.homework.mapper.CommentMapper;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.CommentService;
import ru.skypro.homework.utils.CheckAdmin;
import ru.skypro.homework.utils.CheckAuthentication;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final AdRepository adRepository;
    private final UserRepository userRepository;
    private final CheckAuthentication checkAuthentication;
    private final CheckAdmin checkAdmin;

    public CommentsDTO getComments(Long id, Authentication authentication) {

        checkAuthentication.checkAuthentication(authentication);

        if (adRepository.existsById(id)) {

            List<CommentDTO> commentDTOList = new ArrayList<>();
            for (Comment comment : commentRepository.findCommentsByIdAd(id)) {
                commentDTOList.add(CommentMapper.INSTANCE.commentToCommentDTO(comment, comment.getUser()));
            }

            CommentsDTO commentsDTO = new CommentsDTO();
            commentsDTO.setCount(commentRepository.findCommentsByIdAd(id).size());
            commentsDTO.setResults(commentDTOList);

            return commentsDTO;
        } else {
            log.error("Ad not found");
            throw new EntityNotFoundException("Ad not found");
        }
    }

    public CommentDTO createComment(Long adId, CreateOrUpdateCommentDTO createOrUpdateCommentDTO, Authentication authentication) {

        checkAuthentication.checkAuthentication(authentication);

        if (adRepository.existsById(adId)) {

            LocalDateTime time = LocalDateTime.now();
            User user = userRepository.findByEmail(authentication.getName());
            Ad ad = adRepository.findById(adId).orElse(null);

            Comment comment = CommentMapper.INSTANCE.createOrUpdateCommentDTOToComment(createOrUpdateCommentDTO);

            comment.setCreatedAt(time);
            comment.setUser(user);
            comment.setAd(ad);
            commentRepository.save(comment);
            log.info("Comment created: {}", comment);

            if (user.getImage() == null) {
                log.warn("User {} does not have an image", user.getEmail());
            }

            return CommentMapper.INSTANCE.commentToCommentDTO(comment, user);
        } else {
            log.error("Ad not found");
            throw new EntityNotFoundException("Ad not found");
        }
    }

    public void removalComment(Long adId, Long commentId, Authentication authentication) {

        checkAuthentication.checkAuthentication(authentication);

        if (!adRepository.existsById(adId)) {
            log.error("Ad not found");
            throw new EntityNotFoundException("Ad not found");
        }

        Comment comment = getComment(commentId);

        if (comment.getUser().getEmail().equals(authentication.getName()) || checkAdmin.isAdmin(authentication.getName())) {

            commentRepository.delete(comment);
            log.info("Comment deleted: {}", comment);

        } else {
            throw new ForbiddenException("No authority");
        }
    }

    public CommentDTO editComment(Long adId, Long commentId, CreateOrUpdateCommentDTO createOrUpdateCommentDTO,
                                  Authentication authentication) {

        checkAuthentication.checkAuthentication(authentication);

        if (!adRepository.existsById(adId)) {
            log.error("Ad not found");
            throw new EntityNotFoundException("Ad not found");
        }

        Comment comment = getComment(commentId);

        if (comment.getUser().getEmail().equals(authentication.getName()) || checkAdmin.isAdmin(authentication.getName())) {

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = userRepository.findByEmail(auth.getName());
            comment.setText(createOrUpdateCommentDTO.getText());
            commentRepository.save(comment);
            log.info("Comment edited: {}", comment);
            return CommentMapper.INSTANCE.commentToCommentDTO(comment, user);

        } else {
            throw new ForbiddenException("No authority");
        }
    }

    public Comment getComment(Long pk) {
        return commentRepository.findById(pk).orElseThrow(RuntimeException::new);
    }

    @Override
    public void deleteAll() {
        commentRepository.deleteAll();
    }
}
