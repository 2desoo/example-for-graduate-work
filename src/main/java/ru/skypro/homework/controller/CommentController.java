package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.dto.CommentsDTO;
import ru.skypro.homework.dto.CreateOrUpdateCommentDTO;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.exceptions.EntityNotFoundException;
import ru.skypro.homework.exceptions.ForbiddenException;
import ru.skypro.homework.exceptions.UnauthorizedException;
import ru.skypro.homework.service.CommentService;
import ru.skypro.homework.utils.MethodLog;
import org.springframework.security.core.Authentication;

import java.io.IOException;


@Slf4j
@RestController
@Tag(name = "Комментарии")
@RequiredArgsConstructor
@CrossOrigin(value = "http://localhost:3000")
@RequestMapping("/ads")
public class CommentController {

    private final CommentService commentService;

    @Operation(
            tags = "Комментарии",
            summary = "Получение комментариев объявления"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(
                                    implementation = CommentsDTO.class
                            ))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
    })
    @GetMapping(path = "/{id}/comments")
    public ResponseEntity<CommentsDTO> getComments(@PathVariable Long id,
                                                   Authentication authentication) {
        log.info("Использован метод {}", MethodLog.getMethodName());
        try {
            return ResponseEntity.ok(commentService.getComments(id, authentication));
        } catch (UnauthorizedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (ForbiddenException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @Operation(
            tags = "Комментарии",
            summary = "Добавление комментария к объявлению"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(
                                    implementation = CommentDTO.class
                            ))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @PostMapping(path = "/{id}/comments")
    public ResponseEntity<CommentDTO> createComment(@PathVariable Long id,
                                                 @RequestBody CreateOrUpdateCommentDTO createOrUpdateCommentDTO,
                                                    Authentication authentication) {
        log.info("Использован метод {}", MethodLog.getMethodName());
        try {
            return ResponseEntity.ok(commentService.createComment
                    (id, createOrUpdateCommentDTO, authentication));
        } catch (UnauthorizedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(
            tags = "Комментарии",
            summary = "Удаление комментария"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @DeleteMapping(path = "/{adId}/comments/{commentId}")
    public ResponseEntity<Comment> removalComment(@PathVariable Long adId,
                                                  @PathVariable Long commentId,
                                                  Authentication authentication) {
        log.info("Использован метод {}", MethodLog.getMethodName());
        try {
            commentService.removalComment(adId, commentId, authentication);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (UnauthorizedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (ForbiddenException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(
            tags = "Комментарии",
            summary = "Обновление комментария"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(
                                    implementation = CreateOrUpdateCommentDTO.class
                            ))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)

    })
    @PatchMapping(path = "/{adId}/comments/{commentId}")
    public ResponseEntity<CommentDTO> editComment(@PathVariable Long adId,
                                                                @PathVariable Long commentId,
                                                                @RequestBody CreateOrUpdateCommentDTO createOrUpdateCommentDTO,
                                                  Authentication authentication) {
        log.info("Использован метод {}", MethodLog.getMethodName());
        try {
            return ResponseEntity.ok(commentService.editComment(adId, commentId, createOrUpdateCommentDTO, authentication));
        } catch (UnauthorizedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (ForbiddenException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
