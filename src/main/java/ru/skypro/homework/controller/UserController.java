package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPasswordDto;
import ru.skypro.homework.dto.UpdateUserDTO;
import ru.skypro.homework.dto.UserDTO;
import ru.skypro.homework.utils.MethodLog;

@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@Tag(name = "API для работы с пользователями")
@RequestMapping("/users")
public class UserController {

    @PostMapping("/set_password")
    @Operation(summary = "Обновление пароля", responses = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    public ResponseEntity<Void> setPassword(@RequestBody NewPasswordDto newPasswordDTO) {
        log.info("Использован метод {}", MethodLog.getMethodName());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    @Operation(summary = "Получение информации об авторизованном пользователе", responses = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
    })
    public ResponseEntity<UserDTO> getUser() {
        log.info("Использован метод {}", MethodLog.getMethodName());
        return ResponseEntity.ok(new UserDTO());
    }

    @PatchMapping("/me")
    @Operation(summary = "Обновление информации об авторизованном пользователе", responses = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
    })
    public ResponseEntity<UpdateUserDTO> updateUser(@RequestBody UpdateUserDTO updateUserDTO) {
        log.info("Использован метод {}", MethodLog.getMethodName());
        return ResponseEntity.ok(updateUserDTO);
    }

    @PatchMapping(value = "/me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Обновление аватара авторизованного пользователя", responses = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
    })
    public ResponseEntity<Void> updateUserImage(@RequestPart("image") MultipartFile multipartFile) {
        log.info("Использован метод {}", MethodLog.getMethodName());
        return ResponseEntity.ok().build();
    }
}
