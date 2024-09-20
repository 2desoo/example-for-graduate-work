package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.RegisterDTO;
import ru.skypro.homework.exception.IncorrectPasswordException;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.AuthService;
import ru.skypro.homework.utils.MethodLog;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserMapper mapper;
    private final UserRepository userRepository;
    private final MyUserDetailsService myUserDetailsService;
    private final PasswordEncoder encoder;

    public boolean login(String userName, String password) {
        log.info("Использован метод сервиса: {}", MethodLog.getMethodName());

        UserDetails userDetails = myUserDetailsService.loadUserByUsername(userName);
        log.info("Получен пользователь: {}", userDetails);
        if (!encoder.matches(password, userDetails.getPassword())) {
            log.error("Incorrect password");
            throw new IncorrectPasswordException("Incorrect password");
        }
        log.info("Пользователь авторизирован");
        return true;
    }

    @Override
    public boolean register(RegisterDTO register) {
        log.info("Использован метод сервиса: {}", MethodLog.getMethodName());
        if (userRepository.findByEmail(register.getUsername()) != null) {
            log.info("Пользователь уже зарегистрирован");
            return false;
        }
        register.setPassword(encoder.encode(register.getPassword()));
        userRepository.save(mapper.registerDTOToUser(register));
        return true;
    }

}
