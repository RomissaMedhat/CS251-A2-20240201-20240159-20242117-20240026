package com.budgetapp.ui;

import com.budgetapp.service.AuthService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class LoginController extends BaseController {
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;

    @FXML
    public void onLogin() {
        if (AuthService.getInstance().login(emailField.getText(), passwordField.getText())) {
            loadScreen(emailField, "/fxml/Dashboard.fxml");
        } else {
            errorLabel.setText("Invalid credentials");
        }
    }

    @FXML
    public void onRegister() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Register");
        dialog.setHeaderText("Enter: Name, Email, Password (comma separated)");
        dialog.showAndWait().ifPresent(input -> {
            String[] parts = input.split(",");
            if (parts.length == 3) {
                if (AuthService.getInstance().register(parts[0].trim(), parts[1].trim(), parts[2].trim())) {
                    loadScreen(emailField, "/fxml/Dashboard.fxml");
                } else {
                    errorLabel.setText("Email already exists");
                }
            } else {
                errorLabel.setText("Invalid format");
            }
        });
    }
}