package com.budgetapp.ui;

import com.budgetapp.service.AuthService;
import com.budgetapp.service.BudgetService;
import com.budgetapp.service.CategoryService;
import com.budgetapp.model.Category;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.time.LocalDate;

public class BudgetsController extends BaseController {
    @FXML private ComboBox<String> categoryCombo;
    @FXML private TextField amountField;
    @FXML private TextField monthField, yearField;
    @FXML private ListView<String> budgetsList;
    @FXML private Label statusLabel;

    private int userId;

    @FXML
    public void initialize() {
        userId = AuthService.getInstance().getCurrentUser().get().getUserId();
        for (Category c : CategoryService.getInstance().getUserCategories(userId)) {
            categoryCombo.getItems().add(c.getName());
        }
        refreshList();
    }

    @FXML
    public void onCreateBudget() {
        try {
            int catId = categoryCombo.getSelectionModel().getSelectedIndex() + 1;
            double amount = Double.parseDouble(amountField.getText());
            int month = monthField.getText().isEmpty() ? LocalDate.now().getMonthValue() : Integer.parseInt(monthField.getText());
            int year = yearField.getText().isEmpty() ? LocalDate.now().getYear() : Integer.parseInt(yearField.getText());
            boolean ok = BudgetService.getInstance().createBudget(userId, catId, amount, month, year);
            if (ok) {
                statusLabel.setText("Budget created.");
                refreshList();
            } else {
                statusLabel.setText("Budget already exists for this category/month.");
            }
        } catch (Exception e) {
            statusLabel.setText("Error: " + e.getMessage());
        }
    }

    private void refreshList() {
        budgetsList.getItems().setAll("Budgets will appear here – implement BudgetService.getBudgetsForUser()");
    }
}