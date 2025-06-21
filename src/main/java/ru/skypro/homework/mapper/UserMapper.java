package ru.skypro.homework.mapper;

import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.Register;
import ru.skypro.homework.dto.User;
import ru.skypro.homework.models.UserEntity;

@Component
public class UserMapper {

    public User toDto(UserEntity entity) {
        User dto = new User();
        dto.setId(entity.getId());
        dto.setUsername(entity.getUsername());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setPhone(entity.getPhone());
        dto.setRole(entity.getRole().name());
        dto.setImage(entity.getImage());
        return dto;
    }

    public UserEntity toEntity(Register register) {
        UserEntity entity = new UserEntity();
        entity.setEmail(register.getUsername());
        entity.setUsername(register.getUsername());
        entity.setPassword(register.getPassword()); // хэш будет позже
        entity.setFirstName(register.getFirstName());
        entity.setLastName(register.getLastName());
        entity.setPhone(register.getPhone());
        entity.setRole(register.getRole());
        return entity;
    }
}

