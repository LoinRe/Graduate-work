package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.*;

import java.util.Collections;
import ru.skypro.homework.service.CommentService;

@Tag(name = "Комментарии")
@RestController
@RequestMapping("/ads/{adId}/comments")
@RequiredArgsConstructor
public class CommentsController {
    private final CommentService commentService;

    @Operation(summary = "Комментарии объявления")
    @GetMapping
    public Comments getComments(@PathVariable Integer adId) {
        Comments c = new Comments();
        var all = commentService.getComments(adId);
        c.setCount(all.size());
        c.setResults(all);
        return c;
    }

    @Operation(summary = "Добавить комментарий")
    @PostMapping
    public Comment addComment(@PathVariable Integer adId,
                              @RequestBody CreateOrUpdateComment dto,
                              org.springframework.security.core.Authentication auth) {
        return commentService.addComment(adId, dto, auth);
    }

    @Operation(summary = "Удалить комментарий")
    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteComment(@PathVariable Integer adId,
                              @PathVariable Integer commentId) {
    }

    @Operation(summary = "Обновить комментарий")
    @PatchMapping("/{commentId}")
    public Comment updateComment(@PathVariable Integer adId,
                                 @PathVariable Integer commentId,
                                 @RequestBody CreateOrUpdateComment dto) {
        return new Comment();
    }
}
