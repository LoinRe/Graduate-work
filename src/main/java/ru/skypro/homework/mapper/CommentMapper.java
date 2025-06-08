package ru.skypro.homework.mapper;

import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.Comment;
import ru.skypro.homework.dto.CreateOrUpdateComment;
import ru.skypro.homework.models.CommentEntity;
import ru.skypro.homework.models.UserEntity;

@Component
public class CommentMapper {

    public Comment toDto(CommentEntity entity) {
        Comment dto = new Comment();
        dto.setPk(entity.getId());
        dto.setText(entity.getText());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setAuthor(entity.getAuthor().getId());
        dto.setAuthorFirstName(entity.getAuthor().getFirstName());
        dto.setAuthorImage(entity.getAuthor().getImage());
        return dto;
    }

    public CommentEntity toEntity(CreateOrUpdateComment dto, UserEntity author) {
        CommentEntity entity = new CommentEntity();
        entity.setText(dto.getText());
        entity.setCreatedAt(System.currentTimeMillis());
        entity.setAuthor(author);
        return entity;
    }

    public void updateEntity(CommentEntity entity, CreateOrUpdateComment dto) {
        entity.setText(dto.getText());
    }
}
