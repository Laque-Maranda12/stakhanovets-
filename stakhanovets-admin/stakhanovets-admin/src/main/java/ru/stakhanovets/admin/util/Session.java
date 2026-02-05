package ru.stakhanovets.admin.util;

import ru.stakhanovets.admin.model.User;

public final class Session {
    private Session() {}
    public static String token;
    public static User currentUser;

    public static void clear() { token = null; currentUser = null; }

    public static boolean hasRole(String role) {
        if (currentUser == null || currentUser.getRoles() == null) return false;
        return currentUser.getRoles().stream().anyMatch(r -> r != null && r.equalsIgnoreCase(role));
    }
}
