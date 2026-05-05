package com.budgetapp.ui;

import com.budgetapp.service.AuthService;
import com.budgetapp.service.TransactionService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.time.LocalDate;

public class TransactionHistoryController extends BaseController {
    @FXML private ComboBox<String> categoryFilter;
    @FXML private DatePicker startDate, endDate;
    @FXML private ListView<String> transactionList;

    private int userId;

    @FXML
    public void initialize() {
        userId = AuthService.getInstance().getCurrentUser().get().getUserId();
        categoryFilter.getItems().addAll("All", "Food", "Transport", "Bills");
        startDate.setValue(LocalDate.now().minusMonths(1));
        endDate.setValue(LocalDate.now());
        loadTransactions();
    }

    @FXML
    public void onApplyFilter() { loadTransactions(); }

    private void loadTransactions() {
        var txns = TransactionService.getInstance().getTransactionsByDateRange(userId, startDate.getValue(), endDate.getValue());
        transactionList.getItems().setAll(
                txns.stream().map(t -> t.getDate().toLocalDate() + " | " + t.getType() + " | " + t.getAmount()).toList()
        );
    }
}