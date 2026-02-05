package ru.stakhanovets.admin.api;

import com.fasterxml.jackson.databind.JsonNode;
import ru.stakhanovets.admin.config.ApiConfig;
import ru.stakhanovets.admin.model.User;
import ru.stakhanovets.admin.util.Session;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public final class AuthApi {
    private AuthApi() {}

    public static void login(String email, String password) throws IOException {
        try {
            JsonNode json = ApiClient.postJson("/api/auth/login", Map.of("email", email, "password", password));
            Session.token = json.path("token").asText(null);
            if (Session.token == null || Session.token.isBlank()) throw new IOException("Token is empty");
            Session.currentUser = ApiClient.get("/api/auth/me", User.class);
        } catch (IOException e) {
            if (!ApiConfig.DEMO_FALLBACK) throw e;

            if (email == null || email.isBlank() || password == null || password.isBlank())
                throw new IOException("Bad credentials");

            Session.token = "demo-token";
            List<String> roles = List.of("MANAGER");
            String em = email.toLowerCase();
            if (em.contains("admin")) roles = List.of("ADMIN");
            else if (em.contains("master")) roles = List.of("MASTER");
            Session.currentUser = new User(1, "Демо пользователь", email, true, roles);
        }
    }

    public static void logout() { Session.clear(); }
}
