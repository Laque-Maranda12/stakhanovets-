package ru.stakhanovets.admin.util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public final class AlertUtil {
    private AlertUtil() {}

    public static void info(String title, String text) {
        Alert a = new Alert(Alert.AlertType.INFORMATION, text, ButtonType.OK);
        a.setTitle(title); a.setHeaderText(null); a.showAndWait();
    }

    public static void warn(String title, String text) {
        Alert a = new Alert(Alert.AlertType.WARNING, text, ButtonType.OK);
        a.setTitle(title); a.setHeaderText(null); a.showAndWait();
    }

    public static void error(String title, String text) {
        Alert a = new Alert(Alert.AlertType.ERROR, text, ButtonType.OK);
        a.setTitle(title); a.setHeaderText(null); a.showAndWait();
    }

    public static boolean confirm(String title, String text) {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION, text, ButtonType.OK, ButtonType.CANCEL);
        a.setTitle(title); a.setHeaderText(null);
        return a.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK;
    }
}
