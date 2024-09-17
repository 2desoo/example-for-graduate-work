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
import ru.skypro.homework.entity.User;
import ru.skypro.homework.exceptions.EntityNotFoundException;
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

        createOrUpdateCommentDTO = new CreateOrUpdateCommentDTO();
        createOrUpdateCommentDTO.setText("Test text");
    }

    @Test
    void getComments() {

        Mockito.when(adRepository.existsById(ad.getPk())).thenReturn(true);

        List<Comment> comments = Arrays.asList(new Comment(), new Comment());
        Mockito.when(commentRepository.findCommentsByIdAd(ad.getPk())).thenReturn(comments);

        CommentsDTO result = commentService.getComments(ad.getPk());

        assertNotNull(result);
        assertEquals(2, result.getCount());
        assertEquals(2, result.getResults().size());
    }

    @Test
    void getCommentsEntityNotFoundException() {

        Mockito.when(adRepository.existsById(ad.getPk())).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> commentService.getComments(ad.getPk()));
    }

    @Test
    void createComment() {

        Authentication authentication = Mockito.mock(Authentication.class);

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
    void createCommentEntityNotFoundException() {

        Authentication authentication = Mockito.mock(Authentication.class);

        Mockito.when(adRepository.existsById(ad.getPk())).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> commentService.createComment(
                ad.getPk(),
                createOrUpdateCommentDTO,
                authentication));
    }

    @Test
    void removalComment() {

        Long commentId = 1L;

        Comment comment = new Comment();
        Mockito.when(commentRepository.existsById(commentId)).thenReturn(true);
        Mockito.when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));

        commentService.removalComment(ad.getPk(), commentId);

        Mockito.verify(commentRepository).delete(comment);
    }

    @Test
    void removalCommentEntityNotFoundException() {

        Long commentId = 1L;

        Mockito.when(commentRepository.existsById(commentId)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> commentService.removalComment(ad.getPk(), commentId));
    }

    @Test
    void editComment() {

        Long commentId = 1L;

        Comment comment = new Comment();
        comment.setText("Text");

        Mockito.when(commentRepository.existsById(commentId)).thenReturn(true);
        Mockito.when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));

        CommentDTO result = commentService.editComment(ad.getPk(), commentId, createOrUpdateCommentDTO);

        assertNotNull(result);
        assertEquals("Test text", result.getText());
    }

    @Test
    void editCommentEntityNotFoundException() {

        Long commentId = 1L;

        Mockito.when(commentRepository.existsById(commentId)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> commentService.editComment(ad.getPk(), commentId, createOrUpdateCommentDTO));
    }
}