package ru.stakhanovets.admin.api;

import com.fasterxml.jackson.core.type.TypeReference;
import ru.stakhanovets.admin.config.ApiConfig;
import ru.stakhanovets.admin.model.Tool;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class ToolApi {
    private ToolApi() {}

    public static List<Tool> getAll() throws IOException {
        try {
            return ApiClient.get("/api/tools", new TypeReference<List<Tool>>(){});
        } catch (IOException e) {
            if (!ApiConfig.DEMO_FALLBACK) throw e;
            return demo();
        }
    }

    public static void updateStatus(long id, String status) throws IOException {
        try {
            ApiClient.putJson("/api/tools/" + id + "/status", Map.of("status", status));
        } catch (IOException e) {
            if (!ApiConfig.DEMO_FALLBACK) throw e;
        }
    }

    private static List<Tool> demo() {
        List<Tool> list = new ArrayList<>();
        list.add(new Tool(1, "Перфоратор Bosch GBH 2-26", "AVAILABLE", 500, 3000, 3));
        list.add(new Tool(2, "Шуруповерт Makita DF333", "AVAILABLE", 400, 2500, 5));
        list.add(new Tool(3, "Циркулярная пила Интерскол", "RENTED", 500, 3000, 1));
        list.add(new Tool(4, "Болгарка Metabo", "IN_REPAIR", 500, 3000, 2));
        return list;
    }
}
