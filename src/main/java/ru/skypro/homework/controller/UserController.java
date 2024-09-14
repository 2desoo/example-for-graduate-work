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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPasswordDTO;
import ru.skypro.homework.dto.UpdateUserDTO;
import ru.skypro.homework.dto.UserDTO;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.mapper.UserMapper;
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
    private final UserMapper mapper;
    private final UserService service;

    @PostMapping("/set_password")
    @Operation(summary = "Обновление пароля", responses = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    public ResponseEntity<Void> setPassword(@RequestBody NewPasswordDTO newPasswordDTO) {
        log.warn("POST запрос на смену пароля, тело запроса: {}, метод контроллера: {}", newPasswordDTO, MethodLog.getMethodName());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = repository.findByEmail(auth.getName());
        if (!user.getPassword().equals(newPasswordDTO.getCurrentPassword())) {
            log.error("Неверный пароль");
            return ResponseEntity.status(403).build();
        }
        return ResponseEntity.ok(service.updatePassword(newPasswordDTO));
    }

    @GetMapping("/me")
    @Operation(summary = "Получение информации об авторизованном пользователе", responses = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
    })
    public ResponseEntity<UserDTO> getUser() {
        log.warn("GET запрос на получение активного пользователя, метод контроллера: {}", MethodLog.getMethodName());
        UserDTO userDTO = service.getCurrentUser();
        log.info("Отправлен ответ: {}", userDTO);
        return ResponseEntity.ok(userDTO);
    }

    @PatchMapping("/me")
    @Operation(summary = "Обновление информации об авторизованном пользователе", responses = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
    })
    public ResponseEntity<UpdateUserDTO> updateUser(@RequestBody UpdateUserDTO updateUserDTO) {
        log.warn("PATCH запрос на обновление пользователя, тело запроса: {}, метод контроллера: {}", updateUserDTO, MethodLog.getMethodName());
        User user = repository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        service.updateUser(updateUserDTO);
        repository.save(user);
        log.info("Пользователь обновлен: {}", user);
        return ResponseEntity.ok(updateUserDTO);
    }

    @PatchMapping(value = "/me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Обновление аватара авторизованного пользователя", responses = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
    })
    public ResponseEntity<Void> updateUserImage(@RequestPart(value = "image") MultipartFile multipartFile) {
        log.warn("PATCH запрос на обновление аватара пользователя, тело запроса: MultipartFile image, метод контроллера: {}", MethodLog.getMethodName());
        service.updateUserImage(multipartFile, SecurityContextHolder.getContext().getAuthentication().getName());
        return ResponseEntity.ok().build();
    }
}
