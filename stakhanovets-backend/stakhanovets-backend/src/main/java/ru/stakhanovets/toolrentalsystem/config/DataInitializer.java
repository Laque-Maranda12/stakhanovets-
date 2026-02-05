package ru.stakhanovets.toolrentalsystem.config;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.stakhanovets.toolrentalsystem.model.Category;
import ru.stakhanovets.toolrentalsystem.model.Role;
import ru.stakhanovets.toolrentalsystem.model.Tool;
import ru.stakhanovets.toolrentalsystem.model.ToolStatus;
import ru.stakhanovets.toolrentalsystem.model.User;
import ru.stakhanovets.toolrentalsystem.repository.CategoryRepository;
import ru.stakhanovets.toolrentalsystem.repository.RoleRepository;
import ru.stakhanovets.toolrentalsystem.repository.ToolRepository;
import ru.stakhanovets.toolrentalsystem.repository.UserRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ToolRepository toolRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) {
        initRoles();
        initAdmin();
        initCategories();
        initTools();
    }

    private void initRoles() {
        if (roleRepository.count() == 0) {
            roleRepository.saveAll(List.of(
                    new Role(null, "ADMIN"),
                    new Role(null, "MANAGER"),
                    new Role(null, "MASTER"),
                    new Role(null, "CLIENT")
            ));
        }
    }

    private void initAdmin() {
        if (userRepository.findByEmail("admin@stakhanovets.ru").isEmpty()) {
            User admin = new User();
            admin.setFullName("Администратор");
            admin.setEmail("admin@stakhanovets.ru");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setActive(true);
            admin.setCreatedAt(LocalDateTime.now());

            Role adminRole = roleRepository.findByName("ADMIN")
                    .orElseThrow(() -> new IllegalStateException("Роль ADMIN не найдена"));
            admin.getRoles().add(adminRole);
            userRepository.save(admin);
        }
    }

    private void initCategories() {
        if (categoryRepository.count() == 0) {
            categoryRepository.saveAll(List.of(
                    new Category(null, "Перфораторы", "Перфораторы и отбойные молотки"),
                    new Category(null, "Шуруповёрты", "Аккумуляторные и сетевые шуруповёрты"),
                    new Category(null, "Пилы", "Циркулярные, сабельные и цепные пилы"),
                    new Category(null, "Болгарки", "Угловые шлифовальные машины"),
                    new Category(null, "Сварочное оборудование", "Сварочные аппараты и комплектующие"),
                    new Category(null, "Измерительные приборы", "Лазерные уровни, дальномеры, детекторы")
            ));
        }
    }

    private void initTools() {
        if (toolRepository.count() > 0) return;

        List<Category> categories = categoryRepository.findAll();
        Map<String, List<Object[]>> toolData = Map.of(
                "Перфораторы", List.of(
                        new Object[]{"Перфоратор Bosch GBH 2-26 DRE", "SN-BOS-001", 500, 3000, 12500},
                        new Object[]{"Перфоратор Makita HR2470", "SN-MAK-001", 450, 2500, 10800},
                        new Object[]{"Отбойный молоток DeWalt D25899K", "SN-DEW-001", 800, 5000, 32000}
                ),
                "Шуруповёрты", List.of(
                        new Object[]{"Шуруповёрт Makita DF333DWAE", "SN-MAK-002", 350, 2000, 7500},
                        new Object[]{"Шуруповёрт Bosch GSR 18V-50", "SN-BOS-002", 400, 2500, 9200},
                        new Object[]{"Дрель-шуруповёрт Metabo BS 18 L", "SN-MET-001", 380, 2200, 8100}
                ),
                "Пилы", List.of(
                        new Object[]{"Циркулярная пила Makita HS7601", "SN-MAK-003", 450, 3000, 11500},
                        new Object[]{"Сабельная пила Bosch GSA 1100 E", "SN-BOS-003", 500, 3000, 12000},
                        new Object[]{"Цепная пила Stihl MS 180", "SN-STI-001", 550, 3500, 14900}
                ),
                "Болгарки", List.of(
                        new Object[]{"Болгарка Metabo W 850-125", "SN-MET-002", 350, 2000, 6800},
                        new Object[]{"Болгарка Bosch GWS 22-230 JH", "SN-BOS-004", 500, 3000, 11200}
                ),
                "Сварочное оборудование", List.of(
                        new Object[]{"Сварочный инвертор Ресанта САИ-220", "SN-RES-001", 600, 4000, 18500},
                        new Object[]{"Сварочный полуавтомат Fubag IRMIG 200", "SN-FUB-001", 800, 5000, 29000}
                ),
                "Измерительные приборы", List.of(
                        new Object[]{"Лазерный уровень Bosch GLL 3-80", "SN-BOS-005", 400, 3000, 16500},
                        new Object[]{"Лазерный дальномер Leica DISTO D2", "SN-LEI-001", 300, 2000, 9800},
                        new Object[]{"Детектор проводки Bosch GMS 120", "SN-BOS-006", 300, 2000, 7200}
                )
        );

        for (Category category : categories) {
            List<Object[]> tools = toolData.get(category.getName());
            if (tools == null) continue;
            for (Object[] t : tools) {
                Tool tool = new Tool();
                tool.setCategory(category);
                tool.setName((String) t[0]);
                tool.setSerialNumber((String) t[1]);
                tool.setStatus(ToolStatus.AVAILABLE);
                tool.setPricePerDay((Integer) t[2]);
                tool.setDeposit((Integer) t[3]);
                tool.setStock(1);
                tool.setPurchasePrice(BigDecimal.valueOf((Integer) t[4]));
                tool.setDescription("Профессиональный инструмент для строительных и ремонтных работ");
                tool.setCreatedAt(LocalDateTime.now());
                toolRepository.save(tool);
            }
        }
    }
}
