package ru.stakhanovets.toolrentalsystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.stakhanovets.toolrentalsystem.dto.LoginRequest;
import ru.stakhanovets.toolrentalsystem.dto.RegisterRequest;
import ru.stakhanovets.toolrentalsystem.dto.TokenResponse;
import ru.stakhanovets.toolrentalsystem.dto.UserDto;
import ru.stakhanovets.toolrentalsystem.model.Role;
import ru.stakhanovets.toolrentalsystem.model.User;
import ru.stakhanovets.toolrentalsystem.repository.RoleRepository;
import ru.stakhanovets.toolrentalsystem.repository.UserRepository;
import ru.stakhanovets.toolrentalsystem.security.JwtUtil;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public TokenResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );
        String token = jwtUtil.generateToken(request.email());
        return new TokenResponse(token);
    }

    public TokenResponse register(RegisterRequest request) {
        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new IllegalArgumentException("Email уже зарегистрирован");
        }

        User user = new User();
        user.setFullName(request.fullName());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setActive(true);
        user.setCreatedAt(LocalDateTime.now());

        Role clientRole = roleRepository.findByName("CLIENT")
                .orElseThrow(() -> new IllegalStateException("Роль CLIENT не найдена"));
        user.getRoles().add(clientRole);

        userRepository.save(user);

        String token = jwtUtil.generateToken(request.email());
        return new TokenResponse(token);
    }

    public UserDto getCurrentUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден"));
        return toDto(user);
    }

    private UserDto toDto(User user) {
        List<String> roles = user.getRoles().stream()
                .map(Role::getName)
                .toList();
        return new UserDto(user.getId(), user.getFullName(), user.getEmail(), user.isActive(), roles);
    }
}
