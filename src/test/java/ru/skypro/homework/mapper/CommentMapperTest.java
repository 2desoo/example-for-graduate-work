package ru.skypro.homework.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.dto.CreateOrUpdateCommentDTO;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.entity.Image;
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
        User user = new User();
        user.setId(1);
        user.setEmail("testEmail@mail.ru");
        user.setPassword("password"); // Не забудьте установить пароль
        user.setFirstName("Иван");
        user.setLastName("Иванов");
        user.setPhone("+79993332211");
        user.setRole(Role.USER);
        // Убедитесь, что Image установлен корректно
        user.setImage(new Image());

        Comment comment = new Comment();
        comment.setUser(user);
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
        commentDTO.setPk(1);
        commentDTO.setText("Text test");

        Comment comment = commentMapper.commentDTOToComment(commentDTO);

        assertEquals(1, comment.getUser().getId());
        assertEquals("Иван", comment.getUser().getFirstName());
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
        assertEquals(null, comment.getCreatedAt());
        assertEquals(null, comment.getPk());
    }
}