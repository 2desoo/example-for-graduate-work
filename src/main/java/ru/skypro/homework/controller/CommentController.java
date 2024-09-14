package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.dto.CommentsDTO;
import ru.skypro.homework.dto.CreateOrUpdateCommentDTO;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.service.CommentService;
import ru.skypro.homework.utils.MethodLog;

import java.util.Collection;
import java.util.List;

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
    public ResponseEntity<CommentsDTO> getComments(@PathVariable Integer id) {
        log.info("Использован метод {}", MethodLog.getMethodName());
        CommentsDTO comments = commentService.getComments(id);
        return ResponseEntity.ok(comments);
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
    public ResponseEntity<CommentDTO> createComment(@PathVariable Integer id,
                                                 @RequestBody CreateOrUpdateCommentDTO createOrUpdateCommentDTO) {
        log.info("Использован метод {}", MethodLog.getMethodName());
        CommentDTO createComment = commentService.createComment(id, createOrUpdateCommentDTO);
        return ResponseEntity.ok(createComment);
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
    public ResponseEntity<Comment> removalComment(@PathVariable Integer adId,
                                                  @PathVariable Integer commentId) {
        log.info("Использован метод {}", MethodLog.getMethodName());
        Comment removalComment = commentService.removalComment(adId, commentId);
        return ResponseEntity.ok(removalComment);
    }

    @Operation(
            tags = "Комментарии",
            summary = "Обновление комментария"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(
                                    implementation = Comment.class
                            ))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)

    })
    @PatchMapping(path = "/{adId}/comments/{commentId}")
    public ResponseEntity<CreateOrUpdateCommentDTO> editComment(@PathVariable int adId,
                                                                @PathVariable int commentId,
                                                                @RequestBody CreateOrUpdateCommentDTO createOrUpdateCommentDTO) {
        log.info("Использован метод {}", MethodLog.getMethodName());
        CreateOrUpdateCommentDTO newComment = new CreateOrUpdateCommentDTO();
        return ResponseEntity.ok(newComment);
    }
}
