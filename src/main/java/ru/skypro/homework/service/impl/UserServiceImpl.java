package ru.skypro.homework.service.impl;

import org.springframework.beans.factory.annotation.Value;
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
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.Image;
import ru.skypro.homework.entity.Role;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.exception.IncorrectPasswordException;
import ru.skypro.homework.exception.UserNotFoundException;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.ImageService;
import ru.skypro.homework.service.UserService;
import ru.skypro.homework.utils.CheckAuthentication;
import ru.skypro.homework.utils.MethodLog;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final PasswordEncoder encoder;
    private final UserRepository repository;
    private final UserMapper mapper;
    private final ImageService imageService;
    private final CheckAuthentication checkAuthentication;

    @Value("${path.to.photo.folder}")
    private String photoDir;

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
    public void updateUserImage(MultipartFile image, String userName, Authentication authentication) throws IOException {
        log.info("Использован метод сервиса: {}", MethodLog.getMethodName());

        checkAuthentication.checkAuthentication(authentication);

        User user = repository.findByEmail(userName);

        if (user.getImage() == null) {
            log.info("Пользователь не имеет аватара");

            uploadImageForUser(user.getId(), image);
            log.info("Аватар добавлен");
            return;
        }
        uploadImageForUser(user.getId(), image);
        log.info("Аватар добавлен");
    }

    @Override
    public boolean isAdmin(String email) {
        User user = repository.findByEmail(email);
        return user != null && user.getRole().equals(Role.ADMIN);
    }

    public void uploadImageForUser(Long id, MultipartFile image) throws IOException {
        log.info("Использован метод сервиса: {}", MethodLog.getMethodName());

        User user = repository.findById(id).get();
        log.info("Получено объявление: {}", user);
        Path filePath = Path.of(photoDir, user.getId() + "." + getExtension(image.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);
        try (
                InputStream is = image.getInputStream();
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            bis.transferTo(bos);
        }

        Image image1 = Optional.ofNullable(user.getImage())
                .orElse(new Image());

        image1.setFilePath(filePath.toString());
        image1.setFileSize(image.getSize());
        image1.setMediaType(image.getContentType());
        image1.setData(image.getBytes());
        imageService.saveImage(image1);
        log.info("Изображение сохранено: {}", image1);
        user.setImage(image1);
        repository.save(user);
        log.info("Изображение объявления установлено: {}", user);
    }

    private String getExtension(String filename) {
        return filename.substring(filename.lastIndexOf(".") + 1);
    }
}
