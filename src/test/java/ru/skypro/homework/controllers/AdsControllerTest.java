package ru.skypro.homework.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.skypro.homework.config.WebSecurityConfig;
import ru.skypro.homework.controller.AdsController;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.Role;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.ImageRepository;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.CommentService;
import ru.skypro.homework.service.ImageService;
import ru.skypro.homework.service.UserService;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(WebSecurityConfig.class)
@WebMvcTest(controllers = AdsController.class)
public class AdsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdRepository adRepository;
    @MockBean
    private ImageService imageService;
    @MockBean
    private UserService userService;
    @MockBean
    private CommentService commentService;
    @MockBean
    private AdService adService;
    @SpyBean
    private AdsController adsController;

    User user1 = new User(1, "user@gmail.com", "password",
            "Maria", "Sinyavskaya", "12345678910",
            Role.USER, null, null, null);

   User user2 = new User(2, "user2@gmail.com", "pass123",
           "Ivan", "Ivanov", "10987654321",
           Role.ADMIN, null, null, null);

    Ad ad1 = new Ad(1L, null, 100, "Title",
            "Description", user1, null);

    Ad ad2 = new Ad(2L, null, 200, "Заголовок",
            "Описание", user2, null);
    List<Ad> list = List.of(ad1, ad2);

    @WithMockUser(value = "spring")
    @Test
    public void testGetAll() throws Exception{
        when(adRepository.findAll()).thenReturn(list);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/ads")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
