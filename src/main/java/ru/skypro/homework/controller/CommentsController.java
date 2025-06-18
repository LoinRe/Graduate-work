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
@RequestMapping("/ads/{id}/comments")
@RequiredArgsConstructor
public class CommentsController {
    private final CommentService commentService;

    @Operation(summary = "Комментарии объявления")
    @GetMapping
    public Comments getComments(@PathVariable("id") Integer id) {
        Comments c = new Comments();
        var all = commentService.getComments(id);
        c.setCount(all.size());
        c.setResults(all);
        return c;
    }

    @Operation(summary = "Добавить комментарий")
    @PostMapping
    public Comment addComment(@PathVariable("id") Integer id,
                              @RequestBody CreateOrUpdateComment dto,
                              org.springframework.security.core.Authentication auth) {
        return commentService.addComment(id, dto, auth);
    }

    @Operation(summary = "Удалить комментарий")
    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteComment(@PathVariable("id") Integer id,
                              @PathVariable Integer commentId,
                              org.springframework.security.core.Authentication auth) {
        commentService.deleteComment(commentId, auth);
    }

    @Operation(summary = "Обновить комментарий")
    @PatchMapping("/{commentId}")
    public Comment updateComment(@PathVariable("id") Integer id,
                                 @PathVariable Integer commentId,
                                 @RequestBody CreateOrUpdateComment dto,
                                 org.springframework.security.core.Authentication auth) {
        return commentService.updateComment(commentId, dto, auth);
    }
}

