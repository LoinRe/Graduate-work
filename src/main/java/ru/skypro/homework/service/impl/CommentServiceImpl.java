package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.Comment;
import ru.skypro.homework.dto.CreateOrUpdateComment;
import ru.skypro.homework.mapper.CommentMapper;
import ru.skypro.homework.models.AdEntity;
import ru.skypro.homework.models.CommentEntity;
import ru.skypro.homework.models.UserEntity;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.CommentService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final UserRepository userRepository;
    private final AdRepository adRepository;

    @Override
    public List<Comment> getComments(Integer adId) {
        return commentRepository.findAllByAdId(adId).stream()
                .map(commentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Comment addComment(Integer adId, CreateOrUpdateComment dto, Authentication auth) {
        UserEntity user = userRepository.findByUsername(auth.getName()).orElseThrow();
        AdEntity ad = adRepository.findById(adId).orElseThrow();
        CommentEntity comment = commentMapper.toEntity(dto, user, ad);
        return commentMapper.toDto(commentRepository.save(comment));
    }

    @Override
    public Comment updateComment(Integer commentId, CreateOrUpdateComment dto, Authentication auth) {
        CommentEntity comment = commentRepository.findById(commentId).orElseThrow();
        if (!auth.getName().equals(comment.getAuthor().getUsername()) &&
                !auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            throw new AccessDeniedException("Not allowed");
        }
        comment.setText(dto.getText());
        return commentMapper.toDto(commentRepository.save(comment));
    }

    @Override
    public void deleteComment(Integer commentId, Authentication auth) {
        CommentEntity comment = commentRepository.findById(commentId).orElseThrow();
        if (!auth.getName().equals(comment.getAuthor().getUsername()) &&
                !auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            throw new AccessDeniedException("Not allowed");
        }
        commentRepository.delete(comment);
    }
}
