package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.dto.CommentsDTO;
import ru.skypro.homework.dto.CreateOrUpdateCommentDTO;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.service.CommentService;

import java.util.Collection;

@Slf4j
@RestController
@Tag(name = "Комментарии")
@RequestMapping("/ads")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

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
        CommentsDTO comments = new CommentsDTO();
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
                                    implementation = Comment.class
                            ))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @PostMapping(path = "/{id}/comments")
    public ResponseEntity<Comment> createComment(@PathVariable int id,
                                                 @RequestBody CreateOrUpdateCommentDTO createOrUpdateCommentDTO) {
        Comment createComment = commentService.createComment(id, createOrUpdateCommentDTO);
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
    public ResponseEntity<Comment> removalComment(@PathVariable int adId,
                                                  @PathVariable int commentId) {
        Comment removalComment = commentService.removalComment(commentId);
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
        CreateOrUpdateCommentDTO newComment = new CreateOrUpdateCommentDTO();
        return ResponseEntity.ok(newComment);
    }
}
