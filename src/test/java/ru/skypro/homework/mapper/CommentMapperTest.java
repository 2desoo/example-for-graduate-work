package ru.skypro.homework.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.dto.CreateOrUpdateCommentDTO;
import ru.skypro.homework.entity.*;

import static org.junit.jupiter.api.Assertions.assertEquals;;

class CommentMapperTest {

    private CommentMapper commentMapper;

    @BeforeEach
    void setUp() {
        commentMapper = Mappers.getMapper(CommentMapper.class);
    }

    @Test
    void convertCommentDTO() {

        Comment comment = new Comment();
        comment.setPk(1L);
        comment.setText("Test Comment");

        User user = new User();
        user.setId(1L);
        user.setFirstName("Иван");
        user.setEmail("user@mail.ru");
        comment.setUser(user);

        Ad ad = new Ad();
        ad.setPk(1L);
        comment.setAd(ad);

        CommentDTO commentDto = commentMapper.commentToCommentDTO(comment);

        assertEquals(1L, commentDto.getAuthor());
        assertEquals("user@mail.ru", commentDto.getAuthorImage());
        assertEquals("Иван", commentDto.getAuthorFirstName());
        assertEquals(1L, commentDto.getPk());
        assertEquals("Test Comment", commentDto.getText());
    }

    @Test
    void convertCommentEntity() {

        User user = new User();
        user.setId(1L);
        user.setFirstName("Иван");
        user.setEmail("user@mail.ru");

        Ad ad = new Ad();
        ad.setPk(1L);

        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setAuthor(user.getId());
        commentDTO.setAuthorFirstName(user.getFirstName());
        commentDTO.setAuthorImage(user.getEmail());
        commentDTO.setPk(ad.getPk());
        commentDTO.setText("Text test");

        Comment comment = commentMapper.commentDTOToComment(commentDTO);

        assertEquals(1L, comment.getUser().getId());
        assertEquals("Text test", comment.getText());
    }

    @Test
    void createComment() {

        CreateOrUpdateCommentDTO createOrUpdateCommentDTO = new CreateOrUpdateCommentDTO();
        createOrUpdateCommentDTO.setText("New comment");

        Comment comment = commentMapper.createOrUpdateCommentDTOToComment(createOrUpdateCommentDTO);

        assertEquals("New comment", comment.getText());
        assertEquals(null, comment.getUser());
        assertEquals(null, comment.getCreatedAt());
        assertEquals(null, comment.getPk());
    }
}