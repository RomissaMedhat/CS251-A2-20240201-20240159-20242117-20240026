package com.budgetapp.factory;

import com.budgetapp.model.Budget;
import java.time.LocalDate;

public class BudgetFactory {
    public static Budget createDefaultBudget(int userId, int categoryId) {
        Budget b = new Budget();
        b.setUserId(userId);
        b.setCategoryId(categoryId);
        b.setAlertThreshold(75);
        b.setSpent(0);
        b.setMonth(LocalDate.now().getMonthValue());
        b.setYear(LocalDate.now().getYear());
        return b;
    }

    public static Budget createMonthlyBudget(int userId, int categoryId, double amount, int month, int year) {
        Budget b = createDefaultBudget(userId, categoryId);
        b.setAmount(amount);
        b.setMonth(month);
        b.setYear(year);
        return b;
    }
}