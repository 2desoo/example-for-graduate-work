package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    /**
     * @param id
     * @return
     */
    @Operation(
            tags = "Комментарии",
            description = "Получение комментариев объявления",
            summary = "Получение комментариев объявления"

    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @GetMapping(path = "/{id}/comments")
    public ResponseEntity<Collection<Comment>> getComments(@PathVariable Integer id) {
        Collection<Comment> comments = commentService.getComments();
        return ResponseEntity.ok(comments);
    }

    @Operation(
            tags = "Комментарии",
            description = "Получение комментариев объявления",
            summary = "Добавление комментария к объявлению"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(path = "/{id}/comments")
    public ResponseEntity<Comment> createComment(@RequestBody Comment comment) {
        Comment createComment = commentService.createComment(comment);
        return ResponseEntity.ok(createComment);
    }

    @Operation(
            tags = "Комментарии",
            description = "Удаление комментария",
            summary = "Удаление комментария"
    )
    @DeleteMapping(path = "/{adId}/comments/{commentId}")
    public ResponseEntity<Comment> removalComment(@PathVariable int adId,
                                                  @PathVariable int commentId) {
        Comment removalComment = commentService.removalComment(commentId);
        return ResponseEntity.ok(removalComment);
    }

    @Operation(
            tags = "Комментарии",
            description = "Обновление комментария",
            summary = "Обновление комментария"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Not found")

    })
    @PatchMapping(path = "/{adId}/comments/{commentId}")
    public ResponseEntity<CreateOrUpdateCommentDTO> editComment(@PathVariable int adId,
                                                                @PathVariable int commentId) {
        CreateOrUpdateCommentDTO newComment = new CreateOrUpdateCommentDTO();
        return ResponseEntity.ok(newComment);
    }
}
