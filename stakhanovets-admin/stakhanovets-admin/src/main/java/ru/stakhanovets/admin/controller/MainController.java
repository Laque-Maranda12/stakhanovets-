package ru.stakhanovets.admin.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import ru.stakhanovets.admin.MainApp;
import ru.stakhanovets.admin.api.AuthApi;
import ru.stakhanovets.admin.util.AlertUtil;
import ru.stakhanovets.admin.util.SceneUtil;
import ru.stakhanovets.admin.util.Session;

public class MainController {

    @FXML private Label userLabel;
    @FXML private Label roleLabel;

    @FXML private Button btnTools;
    @FXML private Button btnRentals;
    @FXML private Button btnRepairs;
    @FXML private Button btnUsers;

    @FXML private StackPane content;

    @FXML
    public void initialize() {
        if (Session.currentUser != null) {
            userLabel.setText(Session.currentUser.getEmail());
            roleLabel.setText(Session.currentUser.rolesAsText());
        } else {
            userLabel.setText("—");
            roleLabel.setText("—");
        }

        boolean isAdmin = Session.hasRole("ADMIN");
        boolean isMaster = Session.hasRole("MASTER");
        boolean isManager = Session.hasRole("MANAGER") || isAdmin;

        btnUsers.setVisible(isAdmin);
        btnUsers.setManaged(isAdmin);

        btnRepairs.setDisable(!(isMaster || isAdmin));
        btnRentals.setDisable(!isManager);
        btnTools.setDisable(!isManager);

        openTools();
    }

    private void setCenter(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/fxml/" + fxml));
            Node view = loader.load();
            content.getChildren().setAll(view);
        } catch (Exception e) {
            AlertUtil.error("Ошибка", "Не удалось открыть экран: " + fxml + "\n" + e.getMessage());
        }
    }

    @FXML public void openTools() { setCenter("tools.fxml"); }
    @FXML public void openRentals() { setCenter("rentals.fxml"); }
    @FXML public void openRepairs() { setCenter("repairs.fxml"); }
    @FXML public void openUsers() { setCenter("users.fxml"); }

    @FXML
    public void onLogout() {
        if (!AlertUtil.confirm("Выход", "Выйти из системы?")) return;
        AuthApi.logout();
        SceneUtil.setScene("login.fxml", 460, 520);
    }
}
