package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.RegisterDTO;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.AuthService;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    private final UserDetailsManager manager;
    private final PasswordEncoder encoder;
    private final UserRepository repository;
    private final UserMapper mapper;

    @Override
    public boolean login(String userName, String password) {
        logger.info("Login: " + userName + " Password: " + password);
        if (!manager.userExists(userName)) {
            return false;
        }
        UserDetails userDetails = manager.loadUserByUsername(userName);
        return encoder.matches(password, userDetails.getPassword());
    }

    @Override
    public boolean register(RegisterDTO registerDTO) {
        logger.info("Register: " + registerDTO.getUsername() + " Password: " + registerDTO.getPassword());
        logger.info("Проверка существования пользователя: " + registerDTO.getUsername());
        if (manager.userExists(registerDTO.getUsername())) {
            logger.warn("Пользователь уже существует: " + registerDTO.getUsername());
            return false;
        }
        repository.save(mapper.registerDTOToUser(registerDTO));
        logger.info("Пользователь успешно зарегистрирован: " + registerDTO.getUsername());
        return true;
    }

}
