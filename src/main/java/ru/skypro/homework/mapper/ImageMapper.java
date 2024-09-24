package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;
import ru.skypro.homework.dto.ImageDTO;
import ru.skypro.homework.entity.Image;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ImageMapper {
    ImageMapper INSTANCE = Mappers.getMapper(ImageMapper.class);

    ImageDTO imageToImageDTO(Image image);
    Image imageDTOToImage(ImageDTO imageDTO);
}
