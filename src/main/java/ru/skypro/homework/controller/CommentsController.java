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

    /**
     * Возвращает комментарии к объявлению.
     *
     * @param id идентификатор объявления
     * @return объект {@link Comments} со списком комментариев
     */
    @Operation(summary = "Комментарии объявления")
    @GetMapping
    public Comments getComments(@PathVariable("id") Integer id) {
        Comments c = new Comments();
        var all = commentService.getComments(id);
        c.setCount(all.size());
        c.setResults(all);
        return c;
    }

    /**
     * Добавляет комментарий к объявлению.
     *
     * @param id   идентификатор объявления
     * @param dto  данные комментария
     * @param auth текущий пользователь
     * @return созданный комментарий
     */
    @Operation(summary = "Добавить комментарий")
    @PostMapping
    public Comment addComment(@PathVariable("id") Integer id,
                              @RequestBody CreateOrUpdateComment dto,
                              org.springframework.security.core.Authentication auth) {
        return commentService.addComment(id, dto, auth);
    }

    /**
     * Удаляет комментарий (доступно автору или ADMIN).
     *
     * @param id        идентификатор объявления
     * @param commentId идентификатор комментария
     * @param auth      текущий пользователь
     */
    @Operation(summary = "Удалить комментарий")
    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteComment(@PathVariable("id") Integer id,
                              @PathVariable Integer commentId,
                              org.springframework.security.core.Authentication auth) {
        commentService.deleteComment(commentId, auth);
    }

    /**
     * Обновляет текст комментария.
     *
     * @param id        идентификатор объявления
     * @param commentId идентификатор комментария
     * @param dto       новый текст комментария
     * @param auth      текущий пользователь
     * @return обновлённый комментарий
     */
    @Operation(summary = "Обновить комментарий")
    @PatchMapping("/{commentId}")
    public Comment updateComment(@PathVariable("id") Integer id,
                                 @PathVariable Integer commentId,
                                 @RequestBody CreateOrUpdateComment dto,
                                 org.springframework.security.core.Authentication auth) {
        return commentService.updateComment(commentId, dto, auth);
    }
}

