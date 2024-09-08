package ru.skypro.homework.mappers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.dto.CreateOrUpdateCommentDTO;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.entity.Role;
import ru.skypro.homework.entity.User;

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
        comment.setUser(new User(1, "testEmail@mail.ru",
                "Иван", "Иванов", "+79993332211",
                Role.USER, "the path to the image"));
        comment.setAuthorImage("Ссылка на аватар  комментария");
        comment.setCreatedAt(1633);
        comment.setPk(1);
        comment.setText("Text test");

        CommentDTO commentDTO = commentMapper.commentToCommentDTO(comment);

        assertEquals(1, commentDTO.getAuthor());
        assertEquals("Иван", commentDTO.getAuthorFirstName());
        assertEquals("Ссылка на аватар  комментария", commentDTO.getAuthorImage());
        assertEquals(1633, commentDTO.getCreatedAt());
        assertEquals(1, commentDTO.getPk());
        assertEquals("Text test", commentDTO.getText());
    }

    @Test
    void convertCommentEntity() {

        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setAuthor(1);
        commentDTO.setAuthorFirstName("Иван");
        commentDTO.setAuthorImage("Ссылка на аватар  комментария");
        commentDTO.setCreatedAt(1633);
        commentDTO.setPk(1);
        commentDTO.setText("Text test");

        Comment comment = commentMapper.commentDTOToComment(commentDTO);

        assertEquals(1, comment.getUser().getId());
        assertEquals("Иван", comment.getUser().getFirstName());
        assertEquals("Ссылка на аватар  комментария", comment.getAuthorImage());
        assertEquals(1633, comment.getCreatedAt());
        assertEquals(1, comment.getPk());
        assertEquals("Text test", comment.getText());
    }

    @Test
    void createComment() {

        CreateOrUpdateCommentDTO createOrUpdateCommentDTO = new CreateOrUpdateCommentDTO();
        createOrUpdateCommentDTO.setText("New comment");

        Comment comment = commentMapper.createOrUpdateCommentDTOToComment(createOrUpdateCommentDTO);

        assertEquals("New comment", comment.getText());
        assertEquals(null, comment.getUser());
        assertEquals(null, comment.getAuthorImage());
        assertEquals(null, comment.getCreatedAt());
        assertEquals(null, comment.getPk());
    }
}