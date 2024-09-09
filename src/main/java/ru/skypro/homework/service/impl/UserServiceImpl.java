package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import ru.skypro.homework.dto.UserDTO;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    //    public UserDTO getCurrentUser() {
//        var userDTO = new UserDTO();
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        User user = repository.findByEmail(auth.getName());
//        userDTO.setEmail(user.getEmail());
//        userDTO.setFirstName(user.getFirstName());
//        userDTO.setLastName(user.getLastName());
//        userDTO.setPhone(user.getPhone());
//        userDTO.setRole(user.getRole().name());
//        return userDTO;
//    }
}
