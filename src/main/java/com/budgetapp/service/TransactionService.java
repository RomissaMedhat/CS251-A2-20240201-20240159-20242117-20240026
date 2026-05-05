package com.budgetapp.service;

import com.budgetapp.dao.TransactionDAO;
import com.budgetapp.model.Transaction;
import com.budgetapp.model.TransactionType;
import java.time.LocalDate;
import java.util.List;

public class TransactionService {
    private static TransactionService instance;
    private final TransactionDAO txDAO;

    private TransactionService() { txDAO = new TransactionDAO(); }
    public static synchronized TransactionService getInstance() {
        if (instance == null) instance = new TransactionService();
        return instance;
    }

    public boolean addTransaction(Transaction tx) {
        boolean success = txDAO.create(tx) > 0;
        if (success && tx.getType() == TransactionType.EXPENSE) {
            BudgetService.getInstance().updateBudgetSpent(tx.getUserId(), tx.getCategoryId(), tx.getAmount());
        }
        return success;
    }

    public double getBalance(int userId) { return txDAO.getBalance(userId); }
    public double getMonthlyIncome(int userId, int month, int year) { return txDAO.getMonthlyTotal(userId, TransactionType.INCOME, month, year); }
    public double getMonthlyExpense(int userId, int month, int year) { return txDAO.getMonthlyTotal(userId, TransactionType.EXPENSE, month, year); }

    public List<Transaction> getRecentTransactions(int userId, int limit) {
        List<Transaction> all = txDAO.findByUserId(userId);
        return all.size() > limit ? all.subList(0, limit) : all;
    }

    public List<Transaction> getTransactionsByDateRange(int userId, LocalDate start, LocalDate end) {
        return txDAO.findByDateRange(userId, start, end);
    }

    public List<Transaction> filterByCategoryAndDate(int userId, int categoryId, LocalDate start, LocalDate end) {
        return txDAO.findByCategory(userId, categoryId, start, end);
    }
}