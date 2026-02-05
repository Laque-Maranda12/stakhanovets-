package ru.stakhanovets.toolrentalsystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.stakhanovets.toolrentalsystem.dto.LoginRequest;
import ru.stakhanovets.toolrentalsystem.dto.RegisterRequest;
import ru.stakhanovets.toolrentalsystem.dto.TokenResponse;
import ru.stakhanovets.toolrentalsystem.dto.UserDto;
import ru.stakhanovets.toolrentalsystem.service.AuthService;

import java.security.Principal;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public TokenResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @PostMapping("/register")
    public TokenResponse register(@RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @GetMapping("/me")
    public UserDto me(Principal principal) {
        return authService.getCurrentUser(principal.getName());
    }
}
