package ru.stakhanovets.toolrentalsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.stakhanovets.toolrentalsystem.model.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
