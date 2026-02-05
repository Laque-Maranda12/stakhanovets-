package ru.stakhanovets.toolrentalsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.stakhanovets.toolrentalsystem.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
