package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.dto.CreateOrUpdateCommentDTO;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.entity.User;

/**
 * Сервис для установления соответствия из сущностей в DTO и обратно.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CommentMapper {

    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    /**
     * В DTO {@link CommentDTO} из сущности {@link Comment}.
     */
    @Mapping(target = "author", source = "user.id")
    @Mapping(target = "authorImage", expression = "java(\"/image/\" + user.getImage().getId())")
    @Mapping(target = "authorFirstName", source = "user.firstName")
    CommentDTO commentToCommentDTO(Comment comment, User user);

    /**
     * В сущность {@link Comment} из DTO {@link CommentDTO} .
     */
    @Mappings({
            @Mapping(target = "user.id", source = "author"),
            @Mapping(target = "user.firstName", source = "authorFirstName")
    })
    Comment commentDTOToComment(CommentDTO commentDTO);

    /**
     * В сущность {@link Comment} из DTO {@link CreateOrUpdateCommentDTO} .
     */
    @Mappings({
            @Mapping(target = "user", ignore = true),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "pk", ignore = true)
    })
    Comment createOrUpdateCommentDTOToComment(CreateOrUpdateCommentDTO createOrUpdateCommentDTO);
}
