package ru.skypro.homework.service.impl;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPasswordDTO;
import ru.skypro.homework.dto.UpdateUserDTO;
import ru.skypro.homework.dto.UserDTO;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.exception.IncorrectPasswordException;
import ru.skypro.homework.exception.UserNotFoundException;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.ImageService;
import ru.skypro.homework.service.UserService;
import ru.skypro.homework.utils.CheckAuthentication;
import ru.skypro.homework.utils.MethodLog;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final PasswordEncoder encoder;
    private final UserRepository repository;
    private final UserMapper mapper;
    private final ImageService imageService;
    private final CheckAuthentication checkAuthentication;

    public void updatePassword(NewPasswordDTO passwordDTO,Authentication authentication) {
        log.info("Использован метод сервиса: {}", MethodLog.getMethodName());

        checkAuthentication.checkAuthentication(authentication);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        User user = repository.findByEmail(auth.getName());

        if (user == null) {
            log.error("Пользователь не найден");
            throw new UserNotFoundException("Пользователь не найден");
        }

        if (!encoder.matches(passwordDTO.getCurrentPassword(), user.getPassword())) {
            throw new IncorrectPasswordException("Неверный пароль");
        }

        String hashedPassword = encoder.encode(passwordDTO.getNewPassword());
        user.setPassword(hashedPassword);

        repository.save(user);
    }

    public UserDTO getCurrentUser(Authentication authentication) {
        log.info("Использован метод сервиса: {}", MethodLog.getMethodName());

        checkAuthentication.checkAuthentication(authentication);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        User user = repository.findByEmail(auth.getName());

        return mapper.toUserDTO(user);
    }

    @Override
    public User findByEmail(String login) {
        return repository.findByEmail(login);
    }

    public UserDTO updateUser(UpdateUserDTO updateUserDTO, Authentication authentication) {
        log.info("Использован метод сервиса: {}", MethodLog.getMethodName());

        checkAuthentication.checkAuthentication(authentication);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        User user = repository.findByEmail(auth.getName());

        mapper.updateUserDTOToUser(updateUserDTO, user);

        return mapper.toUserDTO(repository.save(user));
    }

    @Transactional
    public void updateUserImage(MultipartFile image, String userName, Authentication authentication) {
        log.info("Использован метод сервиса: {}", MethodLog.getMethodName());

        checkAuthentication.checkAuthentication(authentication);

        User user = repository.findByEmail(userName);

        if (user.getImage() == null) {
            log.info("Пользователь не имеет аватара");

            user.setImage(imageService.addImage(image));
            log.info("Аватар добавлен");

            repository.save(user);
            return;
        }
        Long imageId = user.getImage().getId();
        user.setImage(imageService.addImage(image));
        log.info("Аватар обновлен");

        imageService.deleteImage(imageId);
        repository.save(user);
    }
}
