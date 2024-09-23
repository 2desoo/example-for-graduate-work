package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPasswordDTO;
import ru.skypro.homework.dto.UpdateUserDTO;
import ru.skypro.homework.dto.UserDTO;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.UserService;
import ru.skypro.homework.utils.MethodLog;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@Tag(name = "API для работы с пользователями")
@RequestMapping("/users")
public class UserController {

    private final UserRepository repository;
    private final UserService service;
    private final PasswordEncoder encoder;

    @PostMapping("/set_password")
    @Operation(summary = "Обновление пароля", responses = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    public ResponseEntity<Void> setPassword(@RequestBody NewPasswordDTO newPasswordDTO, Authentication authentication) {
        log.warn("POST запрос на смену пароля, тело запроса: {}, метод контроллера: {}", newPasswordDTO, MethodLog.getMethodName());

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = repository.findByEmail(auth.getName());
        if (!encoder.matches(newPasswordDTO.getCurrentPassword(), user.getPassword())) {
            log.error("Неверный пароль");
            return ResponseEntity.status(403).build();
        }
        service.updatePassword(newPasswordDTO, authentication);
        log.info("Пароль обновлен");

        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    @Operation(summary = "Получение информации об авторизованном пользователе", responses = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
    })
    public ResponseEntity<UserDTO> getUser(Authentication authentication) {
        log.warn("GET запрос на получение активного пользователя, метод контроллера: {}", MethodLog.getMethodName());

        return ResponseEntity.ok(service.getCurrentUser(authentication));
    }

    @PatchMapping("/me")
    @Operation(summary = "Обновление информации об авторизованном пользователе", responses = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
    })
    public ResponseEntity<UserDTO> updateUser(@RequestBody UpdateUserDTO updateUserDTO, Authentication authentication) {
        log.warn("PATCH запрос на обновление пользователя, тело запроса: {}, метод контроллера: {}", updateUserDTO, MethodLog.getMethodName());

        return ResponseEntity.ok(service.updateUser(updateUserDTO, authentication));
    }

    @PatchMapping(value = "/me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Обновление аватара авторизованного пользователя", responses = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
    })
    public ResponseEntity<?> updateUserImage(@RequestPart(value = "image") MultipartFile multipartFile, Authentication authentication) {
        log.warn("PATCH запрос на обновление аватара пользователя, тело запроса: MultipartFile image, метод контроллера: {}", MethodLog.getMethodName());

        service.updateUserImage(multipartFile, SecurityContextHolder.getContext().getAuthentication().getName(), authentication);

        log.info("Аватар пользователя обновлен");

        return ResponseEntity.ok().build();
    }
}
