package ru.stakhanovets.admin.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.stakhanovets.admin.MainApp;

import java.io.IOException;

public final class SceneUtil {
    private static Stage stage;
    private SceneUtil() {}

    public static void setStage(Stage s) { stage = s; }

    public static void setScene(String fxml, double w, double h) {
        try {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/fxml/" + fxml));
            Parent root = loader.load();
            Scene scene = new Scene(root, w, h);
            scene.getStylesheets().add(MainApp.class.getResource("/styles/app.css").toExternalForm());
            stage.setScene(scene);
        } catch (IOException e) {
            AlertUtil.error("Ошибка", "Не удалось открыть окно: " + fxml + "\n" + e.getMessage());
        }
    }
}
