package ru.skypro.homework.mapper;

import org.junit.jupiter.api.Test;
import ru.skypro.homework.dto.AdDTO;
import ru.skypro.homework.dto.CreateOrUpdateAdDTO;
import ru.skypro.homework.dto.ExtendedAdDTO;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.Image;
import ru.skypro.homework.entity.User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class AdMapperTest {

    private final AdMapper adMapper = AdMapper.INSTANCE;
    @Test
    public void testAdToAdDTO() {
        User user = new User();
        user.setId(1);
        user.setFirstName("Мария");
        user.setLastName("Синявская");

        Image image = new Image();
        image.setId(1L);

        Ad ad = new Ad();
        ad.setPk(1L);
        ad.setUser(user);
        ad.setImage(image);

        AdDTO adDTO = adMapper.adToAdDTO(ad);

        assertEquals(ad.getPk().intValue(), adDTO.getPk());
        assertEquals(ad.getUser().getId(), adDTO.getAuthor());
        assertEquals("/image/" + ad.getImage().getId(), adDTO.getImage());
        }

    @Test
    public void testCreateOrUpdateAdDTOToAd() {
        CreateOrUpdateAdDTO createOrUpdateAdDTO = new CreateOrUpdateAdDTO();
        createOrUpdateAdDTO.setTitle("Заголовок");
        createOrUpdateAdDTO.setPrice(200);
        createOrUpdateAdDTO.setDescription("Описание");

        Ad ad = adMapper.createOrUpdateAdDTOToAd(createOrUpdateAdDTO);

        assertNull(ad.getUser());
        assertNull(ad.getPk());
        assertNull(ad.getComments());
        assertNull(ad.getImage());
        assertEquals(createOrUpdateAdDTO.getPrice(), ad.getPrice());
        assertEquals(createOrUpdateAdDTO.getTitle(), ad.getTitle());
        assertEquals(createOrUpdateAdDTO.getDescription(), ad.getDescription());
    }

    @Test
    public void testToExtendedAdDTO() {
        User user = new User();
        user.setFirstName("Иван");
        user.setLastName("Иванов");
        user.setEmail("user@gmail.com");
        user.setPhone("1234567890");

        Image image = new Image();
        image.setId(2L);

        Ad ad = new Ad();
        ad.setPk(2L);
        ad.setUser(user);
        ad.setImage(image);
        ad.setPrice(200);
        ad.setTitle("Заголовок");
        ad.setDescription("Описание");

        ExtendedAdDTO extendedAdDTO = adMapper.toExtendedAdDTO(ad);

        assertEquals(ad.getPk().intValue(), extendedAdDTO.getPk());
        assertEquals(ad.getUser().getFirstName(), extendedAdDTO.getAuthorFirstName());
        assertEquals(ad.getUser().getLastName(), extendedAdDTO.getAuthorLastName());
        assertEquals(ad.getDescription(), extendedAdDTO.getDescription());
        assertEquals(ad.getUser().getEmail(), extendedAdDTO.getEmail());
        assertEquals("/image/" + ad.getImage().getId(), extendedAdDTO.getImage());
        assertEquals(ad.getUser().getPhone(), extendedAdDTO.getPhone());
        assertEquals(ad.getPrice(), extendedAdDTO.getPrice());
        assertEquals(ad.getTitle(), extendedAdDTO.getTitle());
    }
}
