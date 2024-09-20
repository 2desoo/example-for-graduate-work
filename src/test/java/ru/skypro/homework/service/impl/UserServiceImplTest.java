package ru.skypro.homework.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.skypro.homework.dto.UserDTO;

import ru.skypro.homework.exception.UserNotFoundException;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.ImageService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static ru.skypro.homework.constant.TestConstants.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private PasswordEncoder encoder;

    @Mock
    private UserRepository repository;

    @Mock
    private UserMapper mapper;

    @Mock
    private ImageService imageService;

    @InjectMocks
    private UserServiceImpl service;

    @Test
    void updatePasswordSuccessful() {
        Authentication mockAuthentication = mock(Authentication.class);
        when(mockAuthentication.isAuthenticated()).thenReturn(true);
        when(mockAuthentication.getName()).thenReturn(mockUser.getEmail());

        when(repository.findByEmail(anyString())).thenReturn(mockUser);

        service.updatePassword(mockPasswordDTO);

        verify(repository).save(any());
    }

    @Test
    void updatePasswordFail() {
        Authentication mockAuthentication = mock(Authentication.class);
        when(mockAuthentication.isAuthenticated()).thenReturn(true);
        when(mockAuthentication.getName()).thenReturn(mockUser.getEmail());

        // Мокирование контекста безопасности
        SecurityContext mockSecurityContext = mock(SecurityContext.class);
        when(mockSecurityContext.getAuthentication()).thenReturn(mockAuthentication);

        SecurityContextHolder.setContext(mockSecurityContext);
        when(repository.findByEmail(anyString())).thenReturn(null);

        assertThrows(UserNotFoundException.class, () -> service.updatePassword(mockPasswordDTO));
    }

    @Test
    void getCurrentUser() {
        when(repository.findByEmail(anyString())).thenReturn(mockUser);
        when(mapper.toUserDTO(mockUser)).thenReturn(mockUserDTO);

        UserDTO currentUser = service.getCurrentUser();

        assertEquals(mockUser.getFirstName(),currentUser.getFirstName());
        assertEquals(mockUser.getEmail(),currentUser.getEmail());
        assertEquals(mockUser.getId(),currentUser.getId().longValue());
        assertEquals(mockUser.getRole().name(),currentUser.getRole());
//        assertEquals(mockUser.getImage().getId(),currentUser.getImage());
    }
}