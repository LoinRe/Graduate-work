package ru.skypro.homework.service;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.Authentication;
import ru.skypro.homework.dto.Comment;
import ru.skypro.homework.dto.CreateOrUpdateComment;

import java.util.List;

@Tag(name = "Комментарии")
public interface CommentService {
    List<Comment> getComments(Integer adId);
    Comment addComment(Integer adId, CreateOrUpdateComment dto, Authentication auth);
    Comment updateComment(Integer commentId, CreateOrUpdateComment dto, Authentication auth);
    void deleteComment(Integer commentId, Authentication auth);
}
