package ru.skypro.homework.service;

import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.User;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    boolean changePassword(String email, String oldPass, String newPass);
    User getUser(String email);
    UpdateUser updateUser(String email, UpdateUser dto);
    void updateUserImage(String email, MultipartFile image);
}
