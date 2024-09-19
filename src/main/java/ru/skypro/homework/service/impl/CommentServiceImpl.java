package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.dto.CommentsDTO;
import ru.skypro.homework.dto.CreateOrUpdateCommentDTO;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.exceptions.EntityNotFoundException;
import ru.skypro.homework.exceptions.ForbiddenException;
import ru.skypro.homework.exceptions.UnauthorizedException;
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
@Slf4j
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final AdRepository adRepository;
    private final UserRepository userRepository;

    public CommentsDTO getComments(Long id, Authentication authentication) {

        if (authentication == null || !authentication.isAuthenticated()) {
            log.error("User not authorized");
            throw new UnauthorizedException("User not authorized");
        } else if (adRepository.existsById(id)) {

            List<CommentDTO> list = commentRepository.findCommentsByIdAd(id).stream()
                    .map(CommentMapper.INSTANCE::commentToCommentDTO)
                    .collect(Collectors.toList());

            CommentsDTO commentsDTO = new CommentsDTO();
            commentsDTO.setCount(commentRepository.findCommentsByIdAd(id).size());
            commentsDTO.setResults(list);

            return commentsDTO;
        } else {
            log.error("Ad not found");
            throw new EntityNotFoundException("Ad not found");
        }
    }

    public CommentDTO createComment(Long adId, CreateOrUpdateCommentDTO createOrUpdateCommentDTO, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            log.error("User not authorized");
            throw new UnauthorizedException("User not authorized");
        } else if (adRepository.existsById(adId)) {

            LocalDateTime time = LocalDateTime.now();
            User user = userRepository.findByEmail(authentication.getName());
            Ad ad = adRepository.findById(adId).orElse(null);

            Comment comment = CommentMapper.INSTANCE.createOrUpdateCommentDTOToComment(createOrUpdateCommentDTO);

            comment.setCreatedAt(time);
            comment.setUser(user);
            comment.setAd(ad);
            commentRepository.save(comment);

            return CommentMapper.INSTANCE.commentToCommentDTO(comment);
        } else {
            log.error("Ad not found");
            throw new EntityNotFoundException("Ad not found");
        }
    }

    public void removalComment(Long adId, Long commentId, Authentication authentication) {

        if (authentication == null || !authentication.isAuthenticated()) {
            log.error("User not authorized");
            throw new UnauthorizedException("User not authorized");
        } else if (!adRepository.existsById(adId)) {
            log.error("Ad not found");
            throw new EntityNotFoundException("Ad not found");
        }

        Comment comment = getComment(commentId);

        if (comment.getUser().getEmail().equals(authentication.getName()) || admin(authentication)) {

            commentRepository.delete(comment);

        } else {
            throw new ForbiddenException("No authority");
        }
    }

    public CommentDTO editComment(Long adId, Long commentId, CreateOrUpdateCommentDTO createOrUpdateCommentDTO,
                                  Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            log.error("User not authorized");
            throw new UnauthorizedException("User not authorized");
        } else if (!adRepository.existsById(adId)) {
            log.error("Ad not found");
            throw new EntityNotFoundException("Ad not found");
        }

        Comment comment = getComment(commentId);

        if (comment.getUser().getEmail().equals(authentication.getName()) || admin(authentication)) {

            comment.setText(createOrUpdateCommentDTO.getText());
            commentRepository.save(comment);
            return CommentMapper.INSTANCE.commentToCommentDTO(comment);

        } else {
            throw new ForbiddenException("No authority");
        }
    }

    public boolean admin(Authentication authentication) {
        return authentication.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"));
    }

    public Comment getComment(Long pk) {
        return commentRepository.findById(pk).orElseThrow(RuntimeException::new);
    }

    @Override
    public void deleteAll() {
        commentRepository.deleteAll();
    }
}
