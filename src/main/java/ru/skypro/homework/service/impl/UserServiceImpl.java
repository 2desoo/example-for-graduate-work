package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPasswordDTO;
import ru.skypro.homework.dto.UpdateUserDTO;
import ru.skypro.homework.dto.UserDTO;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.exception.UserNotFoundException;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.ImageService;
import ru.skypro.homework.service.UserService;
import ru.skypro.homework.utils.MethodLog;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final PasswordEncoder encoder;
    private final UserRepository repository;
    private final UserMapper mapper;
    private final ImageService imageService;

    public Void updatePassword(NewPasswordDTO passwordDTO) {
        log.info("Использован метод сервиса: {}", MethodLog.getMethodName());
        log.info("Получен тело запроса: {}", passwordDTO);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = repository.findByEmail(auth.getName());
        log.info("Получен пользователь: {}", user);

        if (user == null) {
            log.error("Пользователь не найден");
            throw new UserNotFoundException("Пользователь не найден");
        }

        user.setPassword(passwordDTO.getNewPassword());
        repository.save(user);
        log.info("Пользователь обновлен: {}", user);
        return null;
    }

    public UserDTO getCurrentUser() {
        log.info("Использован метод сервиса: {}", MethodLog.getMethodName());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = repository.findByEmail(auth.getName());
        log.info("Получен пользователь: {}", user);
        return mapper.toUserDTO(user);
    }

    public UserDTO updateUser(UpdateUserDTO updateUserDTO) {
        log.info("Использован метод сервиса: {}", MethodLog.getMethodName());

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = repository.findByEmail(auth.getName());
        log.info("Получен пользователь: {}", user);

        mapper.updateUserDTOToUser(updateUserDTO, user);

        return mapper.toUserDTO(repository.save(user));
    }

    @Transactional
    public void updateUserImage(MultipartFile image, String userName) {
        log.info("Использован метод сервиса: {}", MethodLog.getMethodName());

        User user = repository.findByEmail(userName);
        log.info("Получен пользователь: {}", user);
        if (user.getImage() == null) {
            user.setImage(imageService.addImage(image));
            repository.save(user);
            return;
        }
        Long imageId = user.getImage().getId();
        user.setImage(imageService.addImage(image));
        imageService.deleteImage(imageId);
        repository.save(user);
    }
}
