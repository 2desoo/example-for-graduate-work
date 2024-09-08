package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import ru.skypro.homework.dto.RegisterDTO;
import ru.skypro.homework.dto.UpdateUserDTO;
import ru.skypro.homework.dto.UserDTO;
import ru.skypro.homework.entity.User;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    @Mapping(target = "email", source = "username")
    @Mapping(target = "password", defaultValue = "pass123")
    User registerDTOToUser(RegisterDTO registerDTO);

    @Mapping(target = "image", expression = "java(user.getImage() != null ? \"/image/\" + user.getImage().getId() : null)")
    @Mapping(source = "role", target = "role")
    UserDTO toUserDTO(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "image", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "ads", ignore = true)
    @Mapping(target = "comments", ignore = true)
    void updateUserDTOToUser(UpdateUserDTO updateUserDTO, @MappingTarget User user);

    UpdateUserDTO userToUpdateUserDTO(User user);
}
