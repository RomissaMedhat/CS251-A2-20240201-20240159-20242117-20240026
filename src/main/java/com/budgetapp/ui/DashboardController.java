package com.budgetapp.ui;

import com.budgetapp.service.*;
import com.budgetapp.model.Transaction;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import java.time.LocalDate;
import java.util.List;

public class DashboardController extends BaseController {
    @FXML private Label balanceLabel, monthlyIncomeLabel, monthlyExpenseLabel;
    @FXML private ListView<String> recentTransactionsList;
    @FXML private ListView<String> alertsList;

    private int userId;

    @FXML
    public void initialize() {
        userId = AuthService.getInstance().getCurrentUser().orElseThrow().getUserId();
        refresh();
    }

    private void refresh() {
        double balance = TransactionService.getInstance().getBalance(userId);
        balanceLabel.setText(String.format("%.2f", balance));
        LocalDate now = LocalDate.now();
        double income = TransactionService.getInstance().getMonthlyIncome(userId, now.getMonthValue(), now.getYear());
        double expense = TransactionService.getInstance().getMonthlyExpense(userId, now.getMonthValue(), now.getYear());
        monthlyIncomeLabel.setText(String.format("%.2f", income));
        monthlyExpenseLabel.setText(String.format("%.2f", expense));

        List<Transaction> recent = TransactionService.getInstance().getRecentTransactions(userId, 5);
        recentTransactionsList.getItems().setAll(
                recent.stream().map(t -> t.getDate().toLocalDate() + " | " + t.getType() + " | " + t.getAmount()).toList()
        );

        List<com.budgetapp.model.Notification> alerts = ((AlertService) AlertService.getInstance()).getUnreadMessages(userId);
        alertsList.getItems().setAll(
                alerts.stream().map(n -> n.getMessage()).toList()
        );
    }

    @FXML public void onAddTransaction() { loadScreen(recentTransactionsList, "/fxml/AddTransaction.fxml"); }
    @FXML public void onBudgets() { loadScreen(recentTransactionsList, "/fxml/Budgets.fxml"); }
    @FXML public void onGoals() { loadScreen(recentTransactionsList, "/fxml/Goals.fxml"); }
    @FXML public void onReports() { loadScreen(recentTransactionsList, "/fxml/Reports.fxml"); }
    @FXML public void onProfile() { loadScreen(recentTransactionsList, "/fxml/Profile.fxml"); }
    @FXML public void onHistory() { loadScreen(recentTransactionsList, "/fxml/TransactionHistory.fxml"); }
    @FXML public void onNotifications() { loadScreen(recentTransactionsList, "/fxml/Notifications.fxml"); }
    @FXML public void onExport() { loadScreen(recentTransactionsList, "/fxml/Export.fxml"); }
    @FXML public void onManageCategories() { loadScreen(recentTransactionsList, "/fxml/ManageCategories.fxml"); }
    @FXML public void onLogout() { logout(recentTransactionsList); }
}