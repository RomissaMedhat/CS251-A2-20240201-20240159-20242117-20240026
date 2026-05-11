package com.budgetapp;

import com.budgetapp.service.AlertService;
import com.budgetapp.service.BudgetService;
import com.budgetapp.ui.BaseController;
import com.budgetapp.util.LanguageUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        BaseController.setPrimaryStage(primaryStage);
        BudgetService.getInstance().addObserver(AlertService.getInstance());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
        loader.setResources(LanguageUtil.getResourceBundle());
        Scene scene = new Scene(loader.load());
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        primaryStage.setTitle("Investors");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}