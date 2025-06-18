package ru.skypro.homework.service;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.Authentication;
import ru.skypro.homework.dto.Comment;
import ru.skypro.homework.dto.CreateOrUpdateComment;

import java.util.List;

/**
 * Сервис для управления комментариями к объявлениям.
 */
@Tag(name = "Комментарии")
public interface CommentService {
    /**
     * Возвращает все комментарии к указанному объявлению.
     * @param adId идентификатор объявления
     * @return список комментариев
     */
    List<Comment> getComments(Integer adId);
    /**
     * Добавляет новый комментарий к объявлению.
     * @param adId идентификатор объявления
     * @param dto  данные комментария
     * @param auth текущий пользователь (автор комментария)
     * @return DTO созданного комментария
     */
    Comment addComment(Integer adId, CreateOrUpdateComment dto, Authentication auth);
    /**
     * Обновляет текст комментария (только автор или ADMIN).
     * @param commentId идентификатор комментария
     * @param dto       новый текст
     * @param auth      текущий пользователь
     * @return DTO обновлённого комментария
     */
    Comment updateComment(Integer commentId, CreateOrUpdateComment dto, Authentication auth);
    /**
     * Удаляет комментарий (только автор или ADMIN).
     * @param commentId идентификатор комментария
     * @param auth      текущий пользователь
     */
    void deleteComment(Integer commentId, Authentication auth);
}
