package ru.stakhanovets.admin.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import ru.stakhanovets.admin.api.RepairApi;
import ru.stakhanovets.admin.model.Repair;
import ru.stakhanovets.admin.util.AlertUtil;

import java.io.IOException;
import java.util.List;

public class RepairsController {

    @FXML private TableView<Repair> table;
    @FXML private TableColumn<Repair, Number> colId;
    @FXML private TableColumn<Repair, String> colTool;
    @FXML private TableColumn<Repair, String> colDate;
    @FXML private TableColumn<Repair, Number> colCost;
    @FXML private TableColumn<Repair, String> colStatus;

    @FXML private ComboBox<String> statusBox;
    @FXML private TextField costField;

    @FXML
    public void initialize() {
        colId.setCellValueFactory(c -> new javafx.beans.property.SimpleLongProperty(c.getValue().getId()));
        colTool.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getToolName()));
        colDate.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getCreatedAt()));
        colCost.setCellValueFactory(c -> new javafx.beans.property.SimpleIntegerProperty(c.getValue().getCost()));
        colStatus.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getStatus()));

        statusBox.setItems(FXCollections.observableArrayList("CREATED","DIAGNOSIS","IN_WORK","READY","CLOSED"));

        table.getSelectionModel().selectedItemProperty().addListener((obs, oldV, v) -> {
            if (v != null) { statusBox.setValue(v.getStatus()); costField.setText(String.valueOf(v.getCost())); }
        });

        reload();
    }

    @FXML
    public void reload() {
        try {
            List<Repair> list = RepairApi.getAll();
            table.setItems(FXCollections.observableArrayList(list));
        } catch (IOException e) {
            AlertUtil.error("Ошибка", "Не удалось загрузить ремонты.\n" + e.getMessage());
        }
    }

    private Repair selected() {
        Repair r = table.getSelectionModel().getSelectedItem();
        if (r == null) AlertUtil.warn("Внимание", "Выберите запись ремонта в таблице.");
        return r;
    }

    @FXML
    public void apply() {
        Repair r = selected(); if (r == null) return;

        String st = statusBox.getValue();
        int cost;
        try {
            cost = Integer.parseInt(costField.getText().trim());
            if (cost < 0) throw new NumberFormatException();
        } catch (Exception e) {
            AlertUtil.warn("Внимание", "Стоимость должна быть числом (>= 0).");
            return;
        }

        try {
            if (st != null && !st.isBlank()) { RepairApi.updateStatus(r.getId(), st); r.setStatus(st); }
            RepairApi.updateCost(r.getId(), cost); r.setCost(cost);
            table.refresh();
            AlertUtil.info("Готово", "Данные ремонта обновлены.");
        } catch (IOException e) {
            AlertUtil.error("Ошибка", "Не удалось обновить ремонт.\n" + e.getMessage());
        }
    }
}
