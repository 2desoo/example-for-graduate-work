package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;
import ru.skypro.homework.dto.AdDTO;
import ru.skypro.homework.dto.CreateOrUpdateAdDTO;
import ru.skypro.homework.dto.ExtendedAdDTO;
import ru.skypro.homework.entity.Ad;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AdMapper {

    AdMapper INSTANCE = Mappers.getMapper(AdMapper.class);

    @Mapping(target = "author", source = "ad.user.id")
    @Mapping(target = "pk", source = "ad.pk")
    @Mapping(target = "image", expression = "java(\"/image/\" + ad.getImage().getId())")
    AdDTO adToAdDTO(Ad ad);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "pk", ignore = true)
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "image", ignore = true)
    Ad createOrUpdateAdDTOToAd(CreateOrUpdateAdDTO createOrUpdateAdDTO);

    @Mapping(target = "pk", source = "ad.pk")
    @Mapping(target = "email", source = "ad.user.email")
    @Mapping(target = "phone", source = "ad.user.phone")
    @Mapping(target = "authorFirstName", source = "ad.user.firstName")
    @Mapping(target = "authorLastName", source = "ad.user.lastName")
    @Mapping(target = "image", expression = "java(\"/image/\" + ad.getImage().getId())")
    ExtendedAdDTO toExtendedAdDTO(Ad ad);
}
