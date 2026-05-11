package com.budgetapp.ui;

import com.budgetapp.service.AuthService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public class LoginController extends BaseController {
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;

    @FXML
    public void onLogin() {
        if (AuthService.getInstance().login(emailField.getText(), passwordField.getText())) {
            loadScreen("/fxml/Dashboard.fxml");
        } else {
            errorLabel.setText("Invalid credentials");
        }
    }

    @FXML
    public void onRegister() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Register");
        dialog.setHeaderText("Create a new account");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        TextField nameField = new TextField();
        nameField.setPromptText("Name");
        TextField emailRegField = new TextField();
        emailRegField.setPromptText("Email");
        PasswordField passField = new PasswordField();
        passField.setPromptText("Password");

        grid.add(new Label("Name:"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(new Label("Email:"), 0, 1);
        grid.add(emailRegField, 1, 1);
        grid.add(new Label("Password:"), 0, 2);
        grid.add(passField, 1, 2);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.showAndWait().ifPresent(button -> {
            if (button == ButtonType.OK) {
                boolean success = AuthService.getInstance().register(
                        nameField.getText(), emailRegField.getText(), passField.getText());
                if (success) {
                    loadScreen("/fxml/Dashboard.fxml");
                } else {
                    errorLabel.setText("Registration failed (email may exist)");
                }
            }
        });
    }
}