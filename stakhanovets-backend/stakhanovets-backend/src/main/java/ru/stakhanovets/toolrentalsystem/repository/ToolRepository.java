package ru.stakhanovets.toolrentalsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.stakhanovets.toolrentalsystem.model.Tool;

public interface ToolRepository extends JpaRepository<Tool, Long> {}
