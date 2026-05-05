package com.budgetapp.service;

import com.budgetapp.dao.BudgetDAO;
import com.budgetapp.factory.BudgetFactory;
import com.budgetapp.model.Budget;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BudgetService {
    private static BudgetService instance;
    private final BudgetDAO budgetDAO;
    private final List<BudgetObserver> observers = new ArrayList<>();

    private BudgetService() { budgetDAO = new BudgetDAO(); }
    public static synchronized BudgetService getInstance() {
        if (instance == null) instance = new BudgetService();
        return instance;
    }

    public void addObserver(BudgetObserver observer) { observers.add(observer); }

    public boolean createBudget(int userId, int categoryId, double amount, int month, int year) {
        if (amount <= 0) throw new IllegalArgumentException("Amount must be positive");
        if (budgetDAO.findByUserCategoryMonth(userId, categoryId, month, year).isPresent())
            return false;
        Budget b = BudgetFactory.createMonthlyBudget(userId, categoryId, amount, month, year);
        return budgetDAO.create(b) > 0;
    }

    public void updateBudgetSpent(int userId, int categoryId, double expenseAmount) {
        LocalDate now = LocalDate.now();
        int month = now.getMonthValue(), year = now.getYear();
        Optional<Budget> opt = budgetDAO.findByUserCategoryMonth(userId, categoryId, month, year);
        if (opt.isEmpty()) return;
        Budget b = opt.get();
        double newSpent = b.getSpent() + expenseAmount;
        b.setSpent(newSpent);
        budgetDAO.update(b);
        double percent = b.getPercentageUsed();
        if (percent >= 100) {
            for (BudgetObserver obs : observers)
                obs.onBudgetExceeded(categoryId, newSpent - b.getAmount());
        } else if (percent >= b.getAlertThreshold()) {
            for (BudgetObserver obs : observers)
                obs.onBudgetWarning(categoryId, percent);
        }
    }
}