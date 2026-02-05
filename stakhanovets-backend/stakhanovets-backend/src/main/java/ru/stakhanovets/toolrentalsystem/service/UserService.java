package ru.stakhanovets.toolrentalsystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.stakhanovets.toolrentalsystem.dto.UserDto;
import ru.stakhanovets.toolrentalsystem.model.Role;
import ru.stakhanovets.toolrentalsystem.model.User;
import ru.stakhanovets.toolrentalsystem.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<UserDto> getAll() {
        return userRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    public void block(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден: " + id));
        user.setActive(false);
        userRepository.save(user);
    }

    private UserDto toDto(User user) {
        List<String> roles = user.getRoles().stream()
                .map(Role::getName)
                .toList();
        return new UserDto(user.getId(), user.getFullName(), user.getEmail(), user.isActive(), roles);
    }
}
