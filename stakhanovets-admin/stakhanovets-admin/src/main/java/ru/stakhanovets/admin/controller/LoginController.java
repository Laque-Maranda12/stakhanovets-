package ru.stakhanovets.admin.controller;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import ru.stakhanovets.admin.api.AuthApi;
import ru.stakhanovets.admin.util.AlertUtil;
import ru.stakhanovets.admin.util.SceneUtil;

public class LoginController {
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;

    @FXML
    public void initialize() {
        emailField.setText("admin@demo.ru");
        passwordField.setText("1234");
    }

    @FXML
    public void onLogin() {
        try {
            AuthApi.login(emailField.getText().trim(), passwordField.getText());
            SceneUtil.setScene("main.fxml", 1100, 700);
        } catch (Exception e) {
            AlertUtil.error("Ошибка входа", "Неправильный E-mail или пароль.\nЕсли сервер не запущен — работает DEMO режим (введите любые данные).");
        }
    }
}
