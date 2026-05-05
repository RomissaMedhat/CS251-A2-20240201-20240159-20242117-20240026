package com.budgetapp.ui;

import com.budgetapp.service.AuthService;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class BaseController {
    protected void loadScreen(Node node, String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Scene scene = new Scene(loader.load());
            scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
            Stage stage = (Stage) node.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) { e.printStackTrace(); }
    }

    protected void goHome(Node node) { loadScreen(node, "/fxml/Dashboard.fxml"); }
    protected void logout(Node node) {
        AuthService.getInstance().logout();
        loadScreen(node, "/fxml/Login.fxml");
    }
}