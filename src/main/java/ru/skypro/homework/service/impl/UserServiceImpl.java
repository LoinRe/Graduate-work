package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.User;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.models.UserEntity;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.UserService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final UserMapper userMapper;

    @Override
    public boolean changePassword(String username, String oldPass, String newPass) {
        Optional<UserEntity> optional = userRepository.findByUsername(username);
        if (optional.isEmpty()) return false;
        UserEntity user = optional.get();
        if (!encoder.matches(oldPass, user.getPassword())) return false;
        user.setPassword(encoder.encode(newPass));
        userRepository.save(user);
        return true;
    }

    @Override
    public User getUser(String username) {
        return userRepository.findByUsername(username)
                .map(userMapper::toDto)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public UpdateUser updateUser(String username, UpdateUser dto) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setPhone(dto.getPhone());
        userRepository.save(user);
        return dto;
    }

    @Override
    public void updateUserImage(String username, MultipartFile image) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (image != null && !image.isEmpty()) {
            try {
                String filename = System.currentTimeMillis() + "_" + image.getOriginalFilename();
                java.nio.file.Path mediaDir = java.nio.file.Paths.get("media");
                if (!java.nio.file.Files.exists(mediaDir)) {
                    java.nio.file.Files.createDirectories(mediaDir);
                }
                java.nio.file.Path filePath = mediaDir.resolve(filename);
                image.transferTo(filePath);
                user.setImage("/media/" + filename);
            } catch (Exception e) {
                throw new RuntimeException("Failed to store image", e);
            }
        }
        userRepository.save(user);
    }
}

