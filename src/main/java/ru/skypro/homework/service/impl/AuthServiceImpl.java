package ru.skypro.homework.service.impl;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.Register;
import ru.skypro.homework.models.UserEntity;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.AuthService;
import ru.skypro.homework.mapper.UserMapper;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    private final PasswordEncoder encoder;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public AuthServiceImpl(PasswordEncoder passwordEncoder,
                           UserRepository userRepository,
                           UserMapper userMapper) {
        this.encoder = passwordEncoder;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public boolean login(String userName, String password) {
        Optional<UserEntity> userOpt = userRepository.findByUsername(userName);
        if (userOpt.isEmpty()) {
            return false;
        }
        UserEntity user = userOpt.get();
        return encoder.matches(password, user.getPassword());
    }

    @Override
    public boolean register(Register register) {
        if (userRepository.findByUsername(register.getUsername()).isPresent()) {
            return false;
        }
        UserEntity entity = userMapper.toEntity(register);
        entity.setPassword(encoder.encode(register.getPassword()));
        userRepository.save(entity);
        return true;
    }
}

