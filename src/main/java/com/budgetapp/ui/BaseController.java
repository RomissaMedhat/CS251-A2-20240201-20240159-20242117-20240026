package com.budgetapp.ui;

import com.budgetapp.service.AuthService;
import com.budgetapp.util.LanguageUtil;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class BaseController {
    private static Stage primaryStage;

    public static void setPrimaryStage(Stage stage) {
        primaryStage = stage;
    }

    protected void loadScreen(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            loader.setResources(LanguageUtil.getResourceBundle());
            Scene scene = new Scene(loader.load());
            scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
            primaryStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void goHome() {
        loadScreen("/fxml/Dashboard.fxml");
    }

    protected void logout() {
        AuthService.getInstance().logout();
        loadScreen("/fxml/Login.fxml");
    }
}