package ru.skypro.homework.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Encoding;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdDTO;
import ru.skypro.homework.dto.AdsDTO;
import ru.skypro.homework.dto.CreateOrUpdateAdDTO;
import ru.skypro.homework.dto.ExtendedAdDTO;
import ru.skypro.homework.exceptions.AccessRightsNotAvailableException;
import ru.skypro.homework.exceptions.AdNotFoundException;
import ru.skypro.homework.exceptions.AdminAccessException;
import ru.skypro.homework.exceptions.UnauthorizedException;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.utils.MethodLog;

import java.io.IOException;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@Tag(name = "Объявления", description = "Контроллер для работы с объявлениями")
@RequiredArgsConstructor
@RequestMapping("/ads")
public class AdsController {

    private final AdService adService;

    @Operation(
            tags = "Объявления",
            summary = "Получение всех объявлений",
            responses = {@ApiResponse(
                    responseCode = "200", description = "OK",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = AdsDTO.class)
                    )
            )})
    @GetMapping()
    public ResponseEntity<?> getAllAds() {
        log.info("Использован метод {}", MethodLog.getMethodName());
        return ResponseEntity.ok(adService.getAllAds());
    }

    @Operation(
            tags = "Объявления",
            summary = "Добавление обьявления",

            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = {@Content(
                            mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
                            encoding = @Encoding(
                                    name = "properties",
                                    contentType = "application/json"
                            ))
                    }
            ),
            responses = {@ApiResponse(responseCode = "201", description = "Created", content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(
                            implementation = AdDTO.class
                    )
            )),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
            }
    )
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addAd(@RequestPart(name = "properties") CreateOrUpdateAdDTO properties,
                                   @RequestPart(name = "image") MultipartFile image,
                                   Authentication authentication) throws IOException {
        log.info("Использован метод {}", MethodLog.getMethodName());
        try {
            return new ResponseEntity<>(adService.addAd(properties, image, authentication), HttpStatus.CREATED);
        } catch (UnauthorizedException | AdminAccessException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @Operation(
            tags = "Объявления",
            summary = "Получение информации об объявлении",
            responses = {@ApiResponse(responseCode = "200", description = "Created", content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(
                            implementation = ExtendedAdDTO.class
                    )

            )),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<?> getAds(@PathVariable Integer id, Authentication authentication) {
        log.info("Использован метод {}", MethodLog.getMethodName());
        try {
            ExtendedAdDTO extendedAdDTO = adService.getById(id, authentication);
            return ResponseEntity.ok(extendedAdDTO);
        } catch (UnauthorizedException | AdminAccessException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (AdNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @Operation(
            tags = "Объявления",
            summary = "Удаление объявления",
            responses = {
                    @ApiResponse(
                            content = @Content(
                                    schema = @Schema(implementation = AdDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "204", description = "No Content", content = @Content),
                    @ApiResponse(
                            responseCode = "401", description = "Unauthorized", content = @Content),
                    @ApiResponse(
                            responseCode = "403", description = "Forbidden", content = @Content),
                    @ApiResponse(
                            responseCode = "404", description = "Not found", content = @Content)
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeAd(@PathVariable Integer id, Authentication authentication) {
        log.info("Использован метод {}", MethodLog.getMethodName());
        try {
            adService.deleteAd(id, authentication);
            return ResponseEntity.noContent().build();
        } catch (UnauthorizedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (AdNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (AccessRightsNotAvailableException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @Operation(
            tags = "Объявления",
            summary = "Обновление информации об объявлении",
            responses = {@ApiResponse(responseCode = "200", description = "OK", content = @Content(
                    schema = @Schema(
                            implementation = AdDTO.class
                    )
            )),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Not found", content = @Content),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
            }
    )
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateAds(@PathVariable Integer id,
                                       @RequestBody CreateOrUpdateAdDTO createOrUpdateAdDTO,
                                       Authentication authentication) {
        log.info("Использован метод {}", MethodLog.getMethodName());
        try {
            return ResponseEntity.ok(adService.updateAd(id, createOrUpdateAdDTO, authentication));
        } catch (UnauthorizedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (AdNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (AccessRightsNotAvailableException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @Operation(
            tags = "Объявления",
            summary = "Получение объявлений авторизованного пользователя",
            responses = {@ApiResponse(responseCode = "200", description = "OK", content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(
                            implementation = AdsDTO.class
                    )
            )),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
            }
    )
    @GetMapping("/me")
    public ResponseEntity<?> getAdsMe(Authentication authentication) {
        log.info("Использован метод {}", MethodLog.getMethodName());
        try {
            AdsDTO adsDTO = adService.getAdsMe(authentication);
            return ResponseEntity.ok(adsDTO);
        } catch (UnauthorizedException | AdminAccessException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @Operation(
            tags = "Объявления",
            summary = "Обновление картинки объявления",
            responses = {@ApiResponse(responseCode = "200", description = "OK", content = @Content(
                    mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE,
                    array = @ArraySchema(
                            schema = @Schema(
                                    type = "string",
                                    format = "byte"
                            )
                    )
            )),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
            }
    )
    @PatchMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateImage(@PathVariable Integer id,
                                         @RequestBody MultipartFile image,
                                         Authentication authentication) throws IOException {
        log.info("Использован метод {}", MethodLog.getMethodName());
        try {
            adService.updateImage(id, image, authentication);
            byte[] imageBytes = image.getBytes();

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(imageBytes);
        } catch (UnauthorizedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (AdNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (AccessRightsNotAvailableException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

    }

}
