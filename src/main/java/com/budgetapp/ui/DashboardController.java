package com.budgetapp.ui;

import com.budgetapp.service.*;
import com.budgetapp.model.Notification;
import com.budgetapp.model.Transaction;
import com.budgetapp.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

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

    @FXML
    public void onHome() { refresh(); }

    private void refresh() {
        User currentUser = AuthService.getInstance().getCurrentUser().orElseThrow();
        double balance = TransactionService.getInstance().getBalance(userId);
        LocalDate now = LocalDate.now();
        double income = TransactionService.getInstance().getMonthlyIncome(userId, now.getMonthValue(), now.getYear());
        double expense = TransactionService.getInstance().getMonthlyExpense(userId, now.getMonthValue(), now.getYear());

        Locale locale = new Locale(currentUser.getLanguage());
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(locale);
        balanceLabel.setText(currencyFormat.format(balance));
        monthlyIncomeLabel.setText(currencyFormat.format(income));
        monthlyExpenseLabel.setText(currencyFormat.format(expense));

        List<Transaction> recent = TransactionService.getInstance().getRecentTransactions(userId, 5);
        recentTransactionsList.getItems().setAll(
                recent.stream().map(t -> t.getDate().toLocalDate() + " | " + t.getType() + " | " + currencyFormat.format(t.getAmount())).toList()
        );

        List<Notification> alerts = AlertService.getInstance().getUnreadMessages(userId);
        alertsList.getItems().setAll(alerts.stream().map(Notification::getMessage).toList());
    }

    @FXML public void onAddTransaction() { loadScreen("/fxml/AddTransaction.fxml"); }
    @FXML public void onBudgets()        { loadScreen("/fxml/Budgets.fxml"); }
    @FXML public void onGoals()          { loadScreen("/fxml/Goals.fxml"); }
    @FXML public void onReports()        { loadScreen("/fxml/Reports.fxml"); }
    @FXML public void onProfile()        { loadScreen("/fxml/Profile.fxml"); }
    @FXML public void onHistory()        { loadScreen("/fxml/TransactionHistory.fxml"); }
    @FXML public void onNotifications()  { loadScreen("/fxml/Notifications.fxml"); }
    @FXML public void onExport()         { loadScreen("/fxml/Export.fxml"); }
    @FXML public void onManageCategories(){ loadScreen("/fxml/ManageCategories.fxml"); }
    @FXML public void onLogout()         { logout(); }
}