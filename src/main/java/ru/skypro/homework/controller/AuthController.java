package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.skypro.homework.dto.Login;
import ru.skypro.homework.dto.Register;
import ru.skypro.homework.service.AuthService;

@Slf4j
@CrossOrigin("http://localhost:3000")
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)          // <─ 200 при успехе
    public void login(@RequestBody Login login) {
        if (!authService.login(login.getUsername(), login.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)     // <─ 201 при успехе
    public void register(@RequestBody Register register) {
        if (!authService.register(register)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
