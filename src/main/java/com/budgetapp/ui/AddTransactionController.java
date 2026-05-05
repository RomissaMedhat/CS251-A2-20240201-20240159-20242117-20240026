package com.budgetapp.ui;

import com.budgetapp.factory.TransactionFactory;
import com.budgetapp.service.AuthService;
import com.budgetapp.service.CategoryService;
import com.budgetapp.service.TransactionService;
import com.budgetapp.model.Category;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.Node;

public class AddTransactionController extends BaseController {
    @FXML private ComboBox<String> typeCombo;
    @FXML private TextField amountField;
    @FXML private ComboBox<String> categoryCombo;
    @FXML private TextArea descriptionArea;
    @FXML private Label statusLabel;

    private int userId;

    @FXML
    public void initialize() {
        userId = AuthService.getInstance().getCurrentUser().get().getUserId();
        typeCombo.getItems().addAll("INCOME", "EXPENSE");
        for (Category c : CategoryService.getInstance().getUserCategories(userId)) {
            categoryCombo.getItems().add(c.getName());
        }
    }

    @FXML
    public void onSave(Node node) {
        try {
            double amount = Double.parseDouble(amountField.getText());
            String type = typeCombo.getValue();
            String category = categoryCombo.getValue();
            int catId = categoryCombo.getSelectionModel().getSelectedIndex() + 1;
            var tx = "INCOME".equals(type)
                    ? TransactionFactory.createIncome(userId, amount, catId, descriptionArea.getText())
                    : TransactionFactory.createExpense(userId, amount, catId, descriptionArea.getText());
            if (TransactionService.getInstance().addTransaction(tx)) {
                statusLabel.setText("Saved! Redirecting...");
                new javafx.animation.PauseTransition(javafx.util.Duration.seconds(1))
                        .setOnFinished(e -> goHome(node));
            } else {
                statusLabel.setText("Save failed.");
            }
        } catch (Exception e) {
            statusLabel.setText("Error: " + e.getMessage());
        }
    }
}