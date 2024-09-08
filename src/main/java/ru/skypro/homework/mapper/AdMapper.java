package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.skypro.homework.dto.AdDTO;
import ru.skypro.homework.dto.CreateOrUpdateAdDTO;
import ru.skypro.homework.dto.ExtendedAdDTO;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.User;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AdMapper {

    @Mapping(target = "author", source = "ad.user.id")
    @Mapping(target = "pk", source = "ad.pk")
    @Mapping(target = "image", expression = "java(\"/image/\" + ad.getImage().getId())")
    AdDTO adToAdDTO(Ad ad);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "pk", ignore = true)
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "image", ignore = true)
    Ad createOrUpdateAdDTOToAd(CreateOrUpdateAdDTO createOrUpdateAdDTO, User user);

    @Mapping(target = "pk", source = "ad.pk")
    @Mapping(target = "authorFirstName", source = "user.firstName")
    @Mapping(target = "authorLastName", source = "user.lastName")
    @Mapping(target = "image", expression = "java(\"/image/\" + ad.getImage().getId())")
    ExtendedAdDTO toExtendedAdDTO (Ad ad, User user);
}
