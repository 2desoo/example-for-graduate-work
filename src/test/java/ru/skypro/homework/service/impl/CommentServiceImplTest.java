package ru.skypro.homework.service.impl;

import org.springframework.security.core.Authentication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.dto.CommentsDTO;
import ru.skypro.homework.dto.CreateOrUpdateCommentDTO;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.entity.Role;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.exception.EntityNotFoundException;
import ru.skypro.homework.exception.ForbiddenException;
import ru.skypro.homework.exception.UnauthorizedException;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.repository.UserRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceImplTest {

    @Mock
    private CommentRepository commentRepository;
    @Mock
    private AdRepository adRepository;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CommentServiceImpl commentService;

    private Ad ad;
    private User user;
    private CreateOrUpdateCommentDTO createOrUpdateCommentDTO;

    @BeforeEach
    void setUp() {

        ad = new Ad();
        ad.setPk(1L);

        user = new User();
        user.setEmail("users@mail.ru");
        user.setRole(Role.USER);

        createOrUpdateCommentDTO = new CreateOrUpdateCommentDTO();
        createOrUpdateCommentDTO.setText("Test text");
    }

    @Test
    void getComments() {

        Authentication authentication = Mockito.mock(Authentication.class);

        Comment comment1 = new Comment();
        comment1.setPk(1L);
        comment1.setAd(ad);
        Comment comment2 = new Comment();
        comment2.setPk(2L);
        comment2.setAd(ad);

        Mockito.when(authentication.isAuthenticated()).thenReturn(true);
        Mockito.when(adRepository.existsById(ad.getPk())).thenReturn(true);

        List<Comment> comments = Arrays.asList(comment1, comment2);

        Mockito.when(commentRepository.findCommentsByIdAd(ad.getPk())).thenReturn(comments);

        CommentsDTO result = commentService.getComments(ad.getPk(), authentication);

        assertNotNull(result);
        assertEquals(2, result.getCount());
        assertEquals(2, result.getResults().size());
    }

    @Test
    void getCommentsUnauthorizedException() {

        Authentication authentication = Mockito.mock(Authentication.class);

        Mockito.when(authentication.isAuthenticated()).thenReturn(false);

        assertThrows(UnauthorizedException.class, () -> commentService.getComments(ad.getPk(), authentication));
    }

    @Test
    void getCommentsEntityNotFoundException() {

        Authentication authentication = Mockito.mock(Authentication.class);

        Mockito.when(authentication.isAuthenticated()).thenReturn(true);
        Mockito.when(adRepository.existsById(ad.getPk())).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> commentService.getComments(ad.getPk(), authentication));
    }

    @Test
    void createComment() {

        Authentication authentication = Mockito.mock(Authentication.class);

        Mockito.when(authentication.isAuthenticated()).thenReturn(true);
        Mockito.when(adRepository.existsById(ad.getPk())).thenReturn(true);
        Mockito.when(userRepository.findByEmail(authentication.getName())).thenReturn(user);
        Mockito.when(adRepository.findById(ad.getPk())).thenReturn(Optional.of(ad));

        Comment savedComment = new Comment();
        savedComment.setText(createOrUpdateCommentDTO.getText());
        Mockito.when(commentRepository.save(Mockito.any(Comment.class))).thenReturn(savedComment);

        CommentDTO result = commentService.createComment(ad.getPk(), createOrUpdateCommentDTO, authentication);

        assertNotNull(result);
        assertEquals(createOrUpdateCommentDTO.getText(), result.getText());
    }

    @Test
    void createCommentUnauthorizedException() {

        Authentication authentication = Mockito.mock(Authentication.class);

        Mockito.when(authentication.isAuthenticated()).thenReturn(false);

        assertThrows(UnauthorizedException.class,
                () -> commentService.createComment(
                        ad.getPk(),
                        createOrUpdateCommentDTO,
                        authentication));
    }

    @Test
    void createCommentEntityNotFoundException() {

        Authentication authentication = Mockito.mock(Authentication.class);

        Mockito.when(authentication.isAuthenticated()).thenReturn(true);
        Mockito.when(adRepository.existsById(ad.getPk())).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> commentService.createComment(
                ad.getPk(),
                createOrUpdateCommentDTO,
                authentication));
    }

    @Test
    void removalComment() {

        Authentication authentication = Mockito.mock(Authentication.class);
        Long commentId = 1L;
        Comment comment = new Comment();
        comment.setPk(commentId);
        comment.setAd(ad);
        comment.setUser(user);

        Mockito.when(authentication.isAuthenticated()).thenReturn(true);
        Mockito.when(adRepository.existsById(ad.getPk())).thenReturn(true);
        Mockito.when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));
        Mockito.when(authentication.getName()).thenReturn(user.getEmail());

        commentService.removalComment(ad.getPk(), commentId, authentication);

        Mockito.verify(commentRepository, Mockito.times(1)).delete(comment);
    }

    @Test
    void removeCommentsUnauthorizedException() {

        Authentication authentication = Mockito.mock(Authentication.class);
        Long commentId = 1L;

        Mockito.when(authentication.isAuthenticated()).thenReturn(false);

        assertThrows(UnauthorizedException.class, () -> commentService.removalComment(ad.getPk(), commentId, authentication));
    }

    @Test
    void removalCommentEntityNotFoundException() {

        Authentication authentication = Mockito.mock(Authentication.class);
        Long commentId = 1L;

        Mockito.when(authentication.isAuthenticated()).thenReturn(true);
        Mockito.when(adRepository.existsById(ad.getPk())).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> commentService.removalComment(ad.getPk(), commentId, authentication));
    }

    @Test
    void removalCommentForbiddenException() {

        Authentication authentication = Mockito.mock(Authentication.class);
        Long commentId = 1L;
        User user1 = new User();
        user1.setEmail("user1@mail.ru");
        Comment comment = new Comment();
        comment.setPk(commentId);
        comment.setAd(ad);
        comment.setUser(user1);

        Mockito.when(authentication.isAuthenticated()).thenReturn(true);
        Mockito.when(adRepository.existsById(ad.getPk())).thenReturn(true);
        Mockito.when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));
        Mockito.when(authentication.getName()).thenReturn(user.getEmail());

        assertThrows(ForbiddenException.class, () -> commentService.removalComment(ad.getPk(), commentId, authentication));
    }

    @Test
    void editCommentUser() {

        Authentication authentication = Mockito.mock(Authentication.class);
        Long commentId = 1L;
        Comment comment = new Comment();
        comment.setPk(commentId);
        comment.setAd(ad);
        comment.setUser(user);

        Mockito.when(authentication.isAuthenticated()).thenReturn(true);
        Mockito.when(adRepository.existsById(ad.getPk())).thenReturn(true);
        Mockito.when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));
        Mockito.when(commentRepository.save(Mockito.any(Comment.class))).thenReturn(new Comment());
        Mockito.when(authentication.getName()).thenReturn(user.getEmail());

        CommentDTO result = commentService.editComment(ad.getPk(), commentId, createOrUpdateCommentDTO, authentication);

        assertNotNull(result);
        assertEquals(createOrUpdateCommentDTO.getText(), result.getText());
    }
}