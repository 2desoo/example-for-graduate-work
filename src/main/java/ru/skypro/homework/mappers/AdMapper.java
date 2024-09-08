package ru.skypro.homework.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import ru.skypro.homework.dto.AdDTO;
import ru.skypro.homework.dto.CreateOrUpdateAdDTO;
import ru.skypro.homework.dto.ExtendedAdDTO;
import ru.skypro.homework.entity.Ad;

@Mapper(componentModel = "spring")
public interface AdMapper {
    AdMapper INSTANCE = Mappers.getMapper(AdMapper.class);

    @Mappings({
            @Mapping(target = "pk", source = "ad.id"),
            @Mapping(target = "author", source = "ad.user.id")
    })
    AdDTO AdToAdDTO(Ad ad);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "image", ignore = true),
            @Mapping(target = "user", ignore = true),
            @Mapping(target = "comments", ignore = true),
    })
    Ad createOrUpdateAdDTOToAd(CreateOrUpdateAdDTO createOrUpdateAdDTO);

    @Mappings({
            @Mapping(target = "pk", source = "ad.id"),
            @Mapping(target = "authorFirstName", source = "user.firstName"),
            @Mapping(target = "authorLastName", source = "user.lastName"),
            @Mapping(target = "email", source = "user.email"),
            @Mapping(target = "phone", source = "user.phone"),
    })
    ExtendedAdDTO AdToExtendedAdDTO(Ad ad);
}
