package ru.stakhanovets.admin.config;

public final class ApiConfig {
    private ApiConfig() {}

    public static final String BASE_URL = "http://localhost:8080";
    public static final boolean DEMO_FALLBACK = true;

    public static final int CONNECT_TIMEOUT_MS = 3000;
    public static final int READ_TIMEOUT_MS = 6000;
}
