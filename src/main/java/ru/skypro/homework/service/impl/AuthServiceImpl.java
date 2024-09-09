package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.RegisterDTO;
import ru.skypro.homework.service.AuthService;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    private final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    private final UserDetailsManager manager;
    private final PasswordEncoder encoder;


    public AuthServiceImpl(UserDetailsManager manager, PasswordEncoder encoder) {
        this.manager = manager;
        this.encoder = encoder;
    }

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
        if (manager.userExists(registerDTO.getUsername())) {
            return false;
        }
        manager.createUser(
                User.builder()
                        .passwordEncoder(this.encoder::encode)
                        .password(registerDTO.getPassword())
                        .username(registerDTO.getUsername())
                        .roles(registerDTO.getRoleDTO().name())
                        .build());
        return true;
    }

}
