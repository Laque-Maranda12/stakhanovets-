package ru.stakhanovets.admin.api;

import com.fasterxml.jackson.core.type.TypeReference;
import ru.stakhanovets.admin.config.ApiConfig;
import ru.stakhanovets.admin.model.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class UserApi {
    private UserApi() {}

    public static List<User> getAll() throws IOException {
        try {
            return ApiClient.get("/api/users", new TypeReference<List<User>>(){});
        } catch (IOException e) {
            if (!ApiConfig.DEMO_FALLBACK) throw e;
            return demo();
        }
    }

    public static void block(long id) throws IOException {
        try {
            ApiClient.putJson("/api/users/" + id + "/block", Map.of());
        } catch (IOException e) {
            if (!ApiConfig.DEMO_FALLBACK) throw e;
        }
    }

    private static List<User> demo() {
        List<User> list = new ArrayList<>();
        list.add(new User(1, "Администратор", "admin@demo.ru", true, List.of("ADMIN")));
        list.add(new User(2, "Менеджер", "manager@demo.ru", true, List.of("MANAGER")));
        list.add(new User(3, "Мастер", "master@demo.ru", true, List.of("MASTER")));
        list.add(new User(4, "Клиент", "client@demo.ru", false, List.of("CLIENT")));
        return list;
    }
}
