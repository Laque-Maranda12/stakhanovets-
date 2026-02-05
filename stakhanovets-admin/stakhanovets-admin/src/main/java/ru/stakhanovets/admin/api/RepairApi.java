package ru.stakhanovets.admin.api;

import com.fasterxml.jackson.core.type.TypeReference;
import ru.stakhanovets.admin.config.ApiConfig;
import ru.stakhanovets.admin.model.Repair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class RepairApi {
    private RepairApi() {}

    public static List<Repair> getAll() throws IOException {
        try {
            return ApiClient.get("/api/repairs", new TypeReference<List<Repair>>(){});
        } catch (IOException e) {
            if (!ApiConfig.DEMO_FALLBACK) throw e;
            return demo();
        }
    }

    public static void updateStatus(long id, String status) throws IOException {
        try {
            ApiClient.putJson("/api/repairs/" + id + "/status", Map.of("status", status));
        } catch (IOException e) {
            if (!ApiConfig.DEMO_FALLBACK) throw e;
        }
    }

    public static void updateCost(long id, int cost) throws IOException {
        try {
            ApiClient.putJson("/api/repairs/" + id + "/cost", Map.of("cost", cost));
        } catch (IOException e) {
            if (!ApiConfig.DEMO_FALLBACK) throw e;
        }
    }

    private static List<Repair> demo() {
        List<Repair> list = new ArrayList<>();
        list.add(new Repair(201, "Болгарка Metabo", "2024-04-22", 1500, "DIAGNOSIS"));
        list.add(new Repair(202, "Перфоратор Bosch GBH 2-26", "2024-04-18", 3000, "IN_WORK"));
        list.add(new Repair(203, "Шуруповерт Makita DF333", "2024-04-05", 800, "CLOSED"));
        return list;
    }
}
