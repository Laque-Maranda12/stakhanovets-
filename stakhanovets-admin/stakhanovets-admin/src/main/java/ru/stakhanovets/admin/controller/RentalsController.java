package ru.stakhanovets.admin.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import ru.stakhanovets.admin.api.RentalApi;
import ru.stakhanovets.admin.model.Rental;
import ru.stakhanovets.admin.util.AlertUtil;

import java.io.IOException;
import java.util.List;

public class RentalsController {

    @FXML private TableView<Rental> table;
    @FXML private TableColumn<Rental, Number> colId;
    @FXML private TableColumn<Rental, String> colTool;
    @FXML private TableColumn<Rental, String> colDate;
    @FXML private TableColumn<Rental, Number> colDays;
    @FXML private TableColumn<Rental, Number> colSum;
    @FXML private TableColumn<Rental, String> colStatus;

    @FXML
    public void initialize() {
        colId.setCellValueFactory(c -> new javafx.beans.property.SimpleLongProperty(c.getValue().getId()));
        colTool.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getToolName()));
        colDate.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getStartDate()));
        colDays.setCellValueFactory(c -> new javafx.beans.property.SimpleIntegerProperty(c.getValue().getDays()));
        colSum.setCellValueFactory(c -> new javafx.beans.property.SimpleIntegerProperty(c.getValue().getSum()));
        colStatus.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getStatus()));
        reload();
    }

    @FXML
    public void reload() {
        try {
            List<Rental> list = RentalApi.getAll();
            table.setItems(FXCollections.observableArrayList(list));
        } catch (IOException e) {
            AlertUtil.error("Ошибка", "Не удалось загрузить заявки аренды.\n" + e.getMessage());
        }
    }

    private Rental selected() {
        Rental r = table.getSelectionModel().getSelectedItem();
        if (r == null) AlertUtil.warn("Внимание", "Выберите заявку в таблице.");
        return r;
    }

    @FXML public void approve() {
        Rental r = selected(); if (r == null) return;
        try { RentalApi.approve(r.getId()); r.setStatus("APPROVED"); table.refresh(); }
        catch (IOException e) { AlertUtil.error("Ошибка", e.getMessage()); }
    }

    @FXML public void issue() {
        Rental r = selected(); if (r == null) return;
        try { RentalApi.issue(r.getId()); r.setStatus("ISSUED"); table.refresh(); }
        catch (IOException e) { AlertUtil.error("Ошибка", e.getMessage()); }
    }

    @FXML public void returned() {
        Rental r = selected(); if (r == null) return;
        try { RentalApi.returned(r.getId()); r.setStatus("RETURNED"); table.refresh(); }
        catch (IOException e) { AlertUtil.error("Ошибка", e.getMessage()); }
    }
}
