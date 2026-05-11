package com.budgetapp.ui;

import com.budgetapp.service.AuthService;
import com.budgetapp.service.CategoryService;
import com.budgetapp.service.TransactionService;
import com.budgetapp.model.Transaction;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.time.LocalDate;
import java.util.List;

public class TransactionHistoryController extends BaseController {
    @FXML private ComboBox<String> categoryFilter;
    @FXML private DatePicker startDate, endDate;
    @FXML private ListView<String> transactionList;

    private int userId;

    @FXML
    public void initialize() {
        userId = AuthService.getInstance().getCurrentUser().get().getUserId();
        var categories = CategoryService.getInstance().getUserCategories(userId);
        categoryFilter.getItems().add("All");
        categories.forEach(c -> categoryFilter.getItems().add(c.getName()));
        categoryFilter.setValue("All");
        startDate.setValue(LocalDate.now().minusMonths(1));
        endDate.setValue(LocalDate.now());
        loadTransactions();
    }

    @FXML
    public void onApplyFilter() { loadTransactions(); }

    @FXML
    public void onHome() { goHome(); }

    private void loadTransactions() {
        List<Transaction> txns;
        if ("All".equals(categoryFilter.getValue())) {
            txns = TransactionService.getInstance().getTransactionsByDateRange(userId, startDate.getValue(), endDate.getValue());
        } else {
            String catName = categoryFilter.getValue();
            int catId = CategoryService.getInstance().getUserCategories(userId).stream()
                    .filter(c -> c.getName().equals(catName))
                    .findFirst().map(c -> c.getCategoryId()).orElse(1);
            txns = TransactionService.getInstance().filterByCategoryAndDate(userId, catId, startDate.getValue(), endDate.getValue());
        }
        transactionList.getItems().setAll(
                txns.stream().map(t -> t.getDate().toLocalDate() + " | " + t.getType() + " | " + t.getAmount() + " | " + t.getDescription()).toList()
        );
        if (txns.isEmpty()) {
            transactionList.setPlaceholder(new Label("No transactions found."));
        }
    }
}