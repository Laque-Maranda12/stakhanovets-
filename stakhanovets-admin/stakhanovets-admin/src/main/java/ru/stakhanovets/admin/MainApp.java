package ru.stakhanovets.admin;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.stakhanovets.admin.util.SceneUtil;

public class MainApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        SceneUtil.setStage(stage);
        FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/fxml/login.fxml"));
        Scene scene = new Scene(loader.load(), 460, 520);
        scene.getStylesheets().add(MainApp.class.getResource("/styles/app.css").toExternalForm());
        stage.setTitle("Стахановец РФ — Админ-панель");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) { launch(args); }
}
