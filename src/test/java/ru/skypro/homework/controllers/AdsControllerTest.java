package ru.skypro.homework.controllers;

import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.config.WebSecurityConfig;
import ru.skypro.homework.controller.AdsController;
import ru.skypro.homework.dto.AdDTO;
import ru.skypro.homework.dto.CreateOrUpdateAdDTO;
import ru.skypro.homework.exception.AccessRightsNotAvailableException;
import ru.skypro.homework.exception.AdNotFoundException;
import ru.skypro.homework.exception.AdminAccessException;
import ru.skypro.homework.exception.UnauthorizedException;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.CommentService;
import ru.skypro.homework.service.ImageService;
import ru.skypro.homework.service.UserService;
import ru.skypro.homework.utils.CheckAuthentication;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.skypro.homework.constant.TestConstants.ad1;
import static ru.skypro.homework.constant.TestConstants.list;

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
    @MockBean
    private CheckAuthentication checkAuthentication;
    @SpyBean
    private AdsController adsController;

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

    @WithMockUser(value = "spring")
    @Test
    public void testAddAd_Success() throws Exception {
        CreateOrUpdateAdDTO createOrUpdateAdDTO = new CreateOrUpdateAdDTO();
        createOrUpdateAdDTO.setTitle("Title");
        createOrUpdateAdDTO.setPrice(100);
        createOrUpdateAdDTO.setDescription("Description");

        MultipartFile image = mock(MultipartFile.class);
        when(image.getBytes()).thenReturn(new byte[]{});

        when(adService.addAd(eq(createOrUpdateAdDTO), any(MultipartFile.class), any()))
                .thenReturn(new AdDTO());

        MockMultipartFile propertiesJson = new MockMultipartFile("properties",
                "properties.json",
                "application/json",
                "{\"title\": \"Title\", \"price\": 100, \"description\": \"Description\"}".getBytes());

        mockMvc.perform(multipart("/ads")
                        .file("image", image.getBytes())
                        .file(propertiesJson)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @WithAnonymousUser
    @Test
    public void testAddAd_Unauthorized() throws Exception {
        CreateOrUpdateAdDTO createOrUpdateAdDTO = new CreateOrUpdateAdDTO();
        createOrUpdateAdDTO.setTitle("Title");
        createOrUpdateAdDTO.setPrice(100);
        createOrUpdateAdDTO.setDescription("Description");

        MultipartFile image = mock(MultipartFile.class);

        when(adService.addAd(eq(createOrUpdateAdDTO), any(MultipartFile.class), any()))
                .thenThrow(new UnauthorizedException(""))
                .thenThrow(new AdminAccessException(""));

        MockMultipartFile propertiesJson = new MockMultipartFile("properties",
                "properties.json",
                "application/json",
                "{\"title\": \"Title\", \"price\": 100, \"description\": \"Description\"}".getBytes());

        mockMvc.perform(multipart("/ads")
                        .file("image", image.getBytes())
                        .file(propertiesJson)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @WithMockUser(value = "spring")
    @Test
    public void testGetAds_Success() throws Exception {
        when(adService.findById(1L)).thenReturn(Optional.of(ad1));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/ads/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @WithAnonymousUser
    @Test
    public void testGetAds_Unauthorized() throws Exception {
        when(adService.getById(anyInt(), any()))
                .thenThrow(new AdminAccessException(""))
                .thenThrow(new UnauthorizedException(""));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/ads/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @WithMockUser(value = "spring")
    @Test
    public void testGetAds_NotFound() throws Exception {
        when(adService.findById(1L)).thenReturn(null);
        when(adService.getById(anyInt(), any()))
                .thenThrow(new AdNotFoundException(""));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/ads/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @WithMockUser(value = "spring")
    @Test
    public void testRemoveAd_Success() throws Exception {
        when(adService.findById(1L)).thenReturn(Optional.of(ad1));
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/ads/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @WithAnonymousUser
    @Test
    public void testRemoveAd_Unauthorized() throws Exception {
        doThrow(new UnauthorizedException(""))
                .when(adService).deleteAd(anyInt(), any());
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/ads/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @WithMockUser(value = "spring")
    @Test
    public void testRemoveAd_NotFound() throws Exception {
        when(adService.findById(1L)).thenReturn(null);
        doThrow(new AdNotFoundException(""))
                .when(adService).deleteAd(anyInt(), any());
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/ads/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @WithMockUser(value = "spring")
    @Test
    public void testRemoveAd_Forbidden() throws Exception {
        when(adService.findById(1L)).thenReturn(Optional.of(ad1));
        doThrow(new AccessRightsNotAvailableException(""))
                .when(adService).deleteAd(anyInt(), any());
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/ads/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @WithMockUser(value = "spring")
    @Test
    public void testUpdateAds_Success() throws Exception {
        JSONObject createOrUpdateAdDTO = new JSONObject();
        createOrUpdateAdDTO.put("title", "Заголовок");
        createOrUpdateAdDTO.put("description", "Новое Описание");
        createOrUpdateAdDTO.put("price", 45);

        when(adService.findById(1L)).thenReturn(Optional.of(ad1));

        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/ads/1")
                        .content(createOrUpdateAdDTO.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @WithAnonymousUser
    @Test
    public void testUpdateAds_Unauthorized() throws Exception {
        JSONObject createOrUpdateAdDTO = new JSONObject();
        createOrUpdateAdDTO.put("title", "Заголовок");
        createOrUpdateAdDTO.put("description", "Новое Описание");
        createOrUpdateAdDTO.put("price", 45);

        when(adService.findById(1L)).thenReturn(Optional.of(ad1));
        when(adService.updateAd(anyInt(), any(), any()))
                .thenThrow(new UnauthorizedException(""));
        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/ads/1")
                        .content(createOrUpdateAdDTO.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @WithMockUser(value = "spring")
    @Test
    public void testUpdateAds_NotFound() throws Exception {
        JSONObject createOrUpdateAdDTO = new JSONObject();
        createOrUpdateAdDTO.put("title", "Заголовок");
        createOrUpdateAdDTO.put("description", "Новое Описание");
        createOrUpdateAdDTO.put("price", 45);

        when(adService.findById(1L)).thenReturn(null);
        when(adService.updateAd(anyInt(), any(), any()))
                .thenThrow(new AdNotFoundException(""));
        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/ads/1")
                        .content(createOrUpdateAdDTO.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @WithMockUser(value = "spring")
    @Test
    public void testUpdateAds_Forbidden() throws Exception {
        JSONObject createOrUpdateAdDTO = new JSONObject();
        createOrUpdateAdDTO.put("title", "Заголовок");
        createOrUpdateAdDTO.put("description", "Новое Описание");
        createOrUpdateAdDTO.put("price", 45);

        when(adService.updateAd(anyInt(), any(), any()))
                .thenThrow(new AccessRightsNotAvailableException(""));
        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/ads/1")
                        .content(createOrUpdateAdDTO.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @WithMockUser(value = "spring")
    @Test
    public void testGetAdsMe_Success() throws Exception {
        when(adRepository.findAll()).thenReturn(list);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/ads/me")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @WithAnonymousUser
    @Test
    public void testGetAdsMe_Unauthorized() throws Exception {
        when(adService.getAdsMe(any()))
                .thenThrow(new UnauthorizedException(""));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/ads/me")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }
}
