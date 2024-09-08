package ru.skypro.homework.mappers;

import org.junit.jupiter.api.Test;
import ru.skypro.homework.dto.AdDTO;
import ru.skypro.homework.dto.CreateOrUpdateAdDTO;
import ru.skypro.homework.dto.ExtendedAdDTO;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.Role;
import ru.skypro.homework.entity.User;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class AdMapperTest {

    @Test
    void shouldProperlyMapAdToAdDTO() {
        Ad ad = new Ad();
        ad.setId(1L);

        User user = new User();
        user.setId(2);
        ad.setUser(user);

        AdDTO adDTO = AdMapper.INSTANCE.AdToAdDTO(ad);

        assertThat(adDTO.getPk()).isEqualTo(ad.getId().intValue());
        assertThat(adDTO.getAuthor()).isEqualTo(ad.getUser().getId());

    }

    @Test
    void shouldProperlyMapCreateOrUpdateAdDTOToAd() {
        CreateOrUpdateAdDTO createAdDTO = new CreateOrUpdateAdDTO();
        createAdDTO.setPrice(1000);
        createAdDTO.setTitle("Заголовок объявления");
        createAdDTO.setDescription("Описание объявления");

        Ad ad = AdMapper.INSTANCE.createOrUpdateAdDTOToAd(createAdDTO);

        assertThat(ad.getPrice()).isEqualTo(createAdDTO.getPrice());
        assertThat(ad.getTitle()).isEqualTo(createAdDTO.getTitle());
        assertThat(ad.getDescription()).isEqualTo(createAdDTO.getDescription());
    }

    @Test
    void shouldProperlyMapAdToExtendedAdDTO() {
        Ad ad = new Ad();
        ad.setId(1L);

        User user = new User();
        user.setId(2);
        user.setEmail("email");
        user.setFirstName("first name");
        user.setLastName("last name");
        user.setPhone("+7-987-654-32-10");
        ad.setUser(user);
        ad.setPrice(1000);
        ad.setTitle("Заголовок объявления");
        ad.setDescription("Описание объявления");

        ExtendedAdDTO extendedAdDTO = AdMapper.INSTANCE.AdToExtendedAdDTO(ad);

        assertThat(extendedAdDTO.getPk()).isEqualTo(ad.getId().intValue());
        assertThat(extendedAdDTO.getAuthorFirstName()).isEqualTo(user.getFirstName());
        assertThat(extendedAdDTO.getAuthorLastName()).isEqualTo(user.getLastName());
        assertThat(extendedAdDTO.getDescription()).isEqualTo(ad.getDescription());
        assertThat(extendedAdDTO.getEmail()).isEqualTo(user.getEmail());
        assertThat(extendedAdDTO.getPhone()).isEqualTo(user.getPhone());
        assertThat(extendedAdDTO.getPrice()).isEqualTo(ad.getPrice());
        assertThat(extendedAdDTO.getTitle()).isEqualTo(ad.getTitle());
    }
}
