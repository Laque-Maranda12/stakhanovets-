package ru.stakhanovets.admin.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import ru.stakhanovets.admin.api.ToolApi;
import ru.stakhanovets.admin.model.Tool;
import ru.stakhanovets.admin.util.AlertUtil;

import java.io.IOException;
import java.util.List;

public class ToolsController {

    @FXML private TableView<Tool> table;
    @FXML private TableColumn<Tool, Number> colId;
    @FXML private TableColumn<Tool, String> colName;
    @FXML private TableColumn<Tool, String> colStatus;
    @FXML private TableColumn<Tool, Number> colPrice;
    @FXML private TableColumn<Tool, Number> colDeposit;
    @FXML private TableColumn<Tool, Number> colStock;

    @FXML private ComboBox<String> statusBox;

    @FXML
    public void initialize() {
        colId.setCellValueFactory(c -> new javafx.beans.property.SimpleLongProperty(c.getValue().getId()));
        colName.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getName()));
        colStatus.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getStatus()));
        colPrice.setCellValueFactory(c -> new javafx.beans.property.SimpleIntegerProperty(c.getValue().getPricePerDay()));
        colDeposit.setCellValueFactory(c -> new javafx.beans.property.SimpleIntegerProperty(c.getValue().getDeposit()));
        colStock.setCellValueFactory(c -> new javafx.beans.property.SimpleIntegerProperty(c.getValue().getStock()));

        statusBox.setItems(FXCollections.observableArrayList("AVAILABLE", "RENTED", "IN_REPAIR", "SOLD"));

        table.getSelectionModel().selectedItemProperty().addListener((obs, oldV, v) -> {
            if (v != null) statusBox.setValue(v.getStatus());
        });

        reload();
    }

    @FXML
    public void reload() {
        try {
            List<Tool> tools = ToolApi.getAll();
            table.setItems(FXCollections.observableArrayList(tools));
        } catch (IOException e) {
            AlertUtil.error("Ошибка", "Не удалось загрузить инструменты.\n" + e.getMessage());
        }
    }

    @FXML
    public void updateStatus() {
        Tool t = table.getSelectionModel().getSelectedItem();
        if (t == null) {
            AlertUtil.warn("Внимание", "Выберите инструмент в таблице.");
            return;
        }
        String newStatus = statusBox.getValue();
        if (newStatus == null || newStatus.isBlank()) return;

        try {
            ToolApi.updateStatus(t.getId(), newStatus);
            t.setStatus(newStatus);
            table.refresh();
            AlertUtil.info("Готово", "Статус инструмента обновлён.");
        } catch (IOException e) {
            AlertUtil.error("Ошибка", "Не удалось обновить статус.\n" + e.getMessage());
        }
    }
}
