package ru.skypro.homework.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.skypro.homework.dto.Register;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.models.UserEntity;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.impl.AuthServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private AuthServiceImpl authService;

    private UserEntity testUser;
    private Register testRegister;

    @BeforeEach
    void setUp() {
        testUser = new UserEntity();
        testUser.setId(1);
        testUser.setUsername("testuser");
        testUser.setPassword("encodedPassword");
        testUser.setEmail("test@example.com");
        testUser.setFirstName("Test");
        testUser.setLastName("User");
        testUser.setPhone("+1234567890");
        testUser.setRole(Role.USER);

        testRegister = new Register();
        testRegister.setUsername("newuser");
        testRegister.setPassword("password123");
        testRegister.setEmail("new@example.com");
        testRegister.setFirstName("New");
        testRegister.setLastName("User");
        testRegister.setPhone("+0987654321");
        testRegister.setRole(Role.USER);
    }

    @Test
    void login_WithValidCredentials_ReturnsTrue() {
        // Given
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(testUser));
        when(passwordEncoder.matches("password123", "encodedPassword")).thenReturn(true);

        // When
        boolean result = authService.login("testuser", "password123");

        // Then
        assertTrue(result);
        verify(userRepository).findByUsername("testuser");
        verify(passwordEncoder).matches("password123", "encodedPassword");
    }

    @Test
    void login_WithInvalidUsername_ReturnsFalse() {
        // Given
        when(userRepository.findByUsername("nonexistent")).thenReturn(Optional.empty());

        // When
        boolean result = authService.login("nonexistent", "password123");

        // Then
        assertFalse(result);
        verify(userRepository).findByUsername("nonexistent");
        verify(passwordEncoder, never()).matches(any(), any());
    }

    @Test
    void login_WithInvalidPassword_ReturnsFalse() {
        // Given
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(testUser));
        when(passwordEncoder.matches("wrongpassword", "encodedPassword")).thenReturn(false);

        // When
        boolean result = authService.login("testuser", "wrongpassword");

        // Then
        assertFalse(result);
        verify(userRepository).findByUsername("testuser");
        verify(passwordEncoder).matches("wrongpassword", "encodedPassword");
    }

    @Test
    void register_WithNewUser_ReturnsTrue() {
        // Given
        when(userRepository.findByUsername("newuser")).thenReturn(Optional.empty());
        when(userMapper.toEntity(testRegister)).thenReturn(testUser);
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");
        when(userRepository.save(any(UserEntity.class))).thenReturn(testUser);

        // When
        boolean result = authService.register(testRegister);

        // Then
        assertTrue(result);
        verify(userRepository).findByUsername("newuser");
        verify(userMapper).toEntity(testRegister);
        verify(passwordEncoder).encode("password123");
        verify(userRepository).save(any(UserEntity.class));
    }

    @Test
    void register_WithExistingUsername_ReturnsFalse() {
        // Given
        when(userRepository.findByUsername("existinguser")).thenReturn(Optional.of(testUser));
        testRegister.setUsername("existinguser");

        // When
        boolean result = authService.register(testRegister);

        // Then
        assertFalse(result);
        verify(userRepository).findByUsername("existinguser");
        verify(userMapper, never()).toEntity(any());
        verify(passwordEncoder, never()).encode(any());
        verify(userRepository, never()).save(any());
    }
} 