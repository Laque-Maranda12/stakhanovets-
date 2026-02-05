package ru.stakhanovets.admin.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import ru.stakhanovets.admin.config.ApiConfig;
import ru.stakhanovets.admin.util.Session;

import java.io.IOException;
import java.time.Duration;
import java.util.Objects;

public final class ApiClient {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final OkHttpClient HTTP = new OkHttpClient.Builder()
            .connectTimeout(Duration.ofMillis(ApiConfig.CONNECT_TIMEOUT_MS))
            .readTimeout(Duration.ofMillis(ApiConfig.READ_TIMEOUT_MS))
            .build();

    private ApiClient() {}

    public static JsonNode getJson(String path) throws IOException {
        Request.Builder b = new Request.Builder().url(ApiConfig.BASE_URL + path).get();
        attachAuth(b);
        return execJson(b.build());
    }

    public static <T> T get(String path, Class<T> type) throws IOException {
        return MAPPER.treeToValue(getJson(path), type);
    }

    public static <T> T get(String path, TypeReference<T> type) throws IOException {
        return MAPPER.convertValue(getJson(path), type);
    }

    public static JsonNode postJson(String path, Object body) throws IOException {
        String json = MAPPER.writeValueAsString(body);
        RequestBody rb = RequestBody.create(json, MediaType.parse("application/json"));
        Request.Builder b = new Request.Builder().url(ApiConfig.BASE_URL + path).post(rb);
        attachAuth(b);
        return execJson(b.build());
    }

    public static JsonNode putJson(String path, Object body) throws IOException {
        String json = MAPPER.writeValueAsString(body);
        RequestBody rb = RequestBody.create(json, MediaType.parse("application/json"));
        Request.Builder b = new Request.Builder().url(ApiConfig.BASE_URL + path).put(rb);
        attachAuth(b);
        return execJson(b.build());
    }

    private static void attachAuth(Request.Builder b) {
        if (Session.token != null && !Session.token.isBlank()) {
            b.header("Authorization", "Bearer " + Session.token);
        }
        b.header("Accept", "application/json");
    }

    private static JsonNode execJson(Request req) throws IOException {
        try (Response res = HTTP.newCall(req).execute()) {
            if (!res.isSuccessful()) {
                String body = res.body() != null ? res.body().string() : "";
                throw new IOException("HTTP " + res.code() + ": " + body);
            }
            String body = Objects.requireNonNull(res.body()).string();
            if (body == null || body.isBlank()) return MAPPER.createObjectNode();
            return MAPPER.readTree(body);
        }
    }
}
