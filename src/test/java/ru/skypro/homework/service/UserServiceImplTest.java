package ru.skypro.homework.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.User;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.models.UserEntity;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.impl.UserServiceImpl;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit-тесты {@link UserServiceImpl}. Проверяется бизнес-логика без участия базы и Spring-контекста.
 */
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder encoder;
    @Mock
    private UserMapper userMapper;
    @Mock
    private MultipartFile multipartFile;

    @InjectMocks
    private UserServiceImpl userService;

    private UserEntity userEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userEntity = new UserEntity();
        userEntity.setId(1);
        userEntity.setUsername("john");
        userEntity.setPassword("encodedOld");
    }

    @Nested
    @DisplayName("changePassword()")
    class ChangePassword {

        @Test
        @DisplayName("возвращает true при корректном старом пароле")
        void changePassword_success() {
            // given
            when(userRepository.findByUsername("john")).thenReturn(Optional.of(userEntity));
            when(encoder.matches("old", "encodedOld")).thenReturn(true);
            when(encoder.encode("new")).thenReturn("encodedNew");

            // when
            boolean result = userService.changePassword("john", "old", "new");

            // then
            assertThat(result).isTrue();
            assertThat(userEntity.getPassword()).isEqualTo("encodedNew");
            verify(userRepository).save(userEntity);
        }

        @Test
        @DisplayName("возвращает false при неверном старом пароле")
        void changePassword_wrongOld() {
            when(userRepository.findByUsername("john")).thenReturn(Optional.of(userEntity));
            when(encoder.matches("wrong", "encodedOld")).thenReturn(false);

            boolean result = userService.changePassword("john", "wrong", "new");

            assertThat(result).isFalse();
            verify(userRepository, never()).save(any());
        }

        @Test
        @DisplayName("возвращает false, если пользователь не найден")
        void changePassword_userNotFound() {
            when(userRepository.findByUsername("john")).thenReturn(Optional.empty());

            boolean result = userService.changePassword("john", "old", "new");

            assertThat(result).isFalse();
            verify(userRepository, never()).save(any());
        }
    }

    @Test
    @DisplayName("getUser() возвращает DTO при успешном поиске")
    void getUser_success() {
        when(userRepository.findByUsername("john")).thenReturn(Optional.of(userEntity));
        User dto = new User();
        dto.setId(1);
        dto.setUsername("john");
        when(userMapper.toDto(userEntity)).thenReturn(dto);

        User result = userService.getUser("john");

        assertThat(result).isEqualTo(dto);
    }

    @Test
    @DisplayName("getUser() бросает RuntimeException, если пользователь не найден")
    void getUser_notFound() {
        when(userRepository.findByUsername("john")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userService.getUser("john"));
    }
}
