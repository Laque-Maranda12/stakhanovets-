package ru.stakhanovets.admin.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import ru.stakhanovets.admin.api.UserApi;
import ru.stakhanovets.admin.model.User;
import ru.stakhanovets.admin.util.AlertUtil;

import java.io.IOException;
import java.util.List;

public class UsersController {

    @FXML private TableView<User> table;
    @FXML private TableColumn<User, Number> colId;
    @FXML private TableColumn<User, String> colName;
    @FXML private TableColumn<User, String> colEmail;
    @FXML private TableColumn<User, String> colRoles;
    @FXML private TableColumn<User, String> colActive;

    @FXML
    public void initialize() {
        colId.setCellValueFactory(c -> new javafx.beans.property.SimpleLongProperty(c.getValue().getId()));
        colName.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getFullName()));
        colEmail.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getEmail()));
        colRoles.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().rolesAsText()));
        colActive.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().isActive() ? "Да" : "Нет"));
        reload();
    }

    @FXML
    public void reload() {
        try {
            List<User> list = UserApi.getAll();
            table.setItems(FXCollections.observableArrayList(list));
        } catch (IOException e) {
            AlertUtil.error("Ошибка", "Не удалось загрузить пользователей.\n" + e.getMessage());
        }
    }

    @FXML
    public void block() {
        User u = table.getSelectionModel().getSelectedItem();
        if (u == null) { AlertUtil.warn("Внимание", "Выберите пользователя."); return; }
        if (!AlertUtil.confirm("Подтверждение", "Заблокировать пользователя: " + u.getEmail() + " ?")) return;

        try {
            UserApi.block(u.getId());
            u.setActive(false);
            table.refresh();
        } catch (IOException e) {
            AlertUtil.error("Ошибка", "Не удалось заблокировать.\n" + e.getMessage());
        }
    }
}
