package ru.stakhanovets.admin.api;

import com.fasterxml.jackson.core.type.TypeReference;
import ru.stakhanovets.admin.config.ApiConfig;
import ru.stakhanovets.admin.model.Rental;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class RentalApi {
    private RentalApi() {}

    public static List<Rental> getAll() throws IOException {
        try {
            return ApiClient.get("/api/rentals", new TypeReference<List<Rental>>(){});
        } catch (IOException e) {
            if (!ApiConfig.DEMO_FALLBACK) throw e;
            return demo();
        }
    }

    public static void approve(long id) throws IOException { action(id, "approve"); }
    public static void issue(long id) throws IOException { action(id, "issue"); }
    public static void returned(long id) throws IOException { action(id, "return"); }

    private static void action(long id, String act) throws IOException {
        try {
            ApiClient.putJson("/api/rentals/" + id + "/" + act, new Object());
        } catch (IOException e) {
            if (!ApiConfig.DEMO_FALLBACK) throw e;
        }
    }

    private static List<Rental> demo() {
        List<Rental> list = new ArrayList<>();
        list.add(new Rental(125, "Bosch GBH 2-26", "2024-04-24", 1, 3500, "REQUESTED"));
        list.add(new Rental(124, "Makita HR2470", "2024-04-10", 4, 1800, "RETURNED"));
        list.add(new Rental(123, "Stihl MS 180", "2024-04-01", 2, 1200, "CANCELED"));
        return list;
    }
}
