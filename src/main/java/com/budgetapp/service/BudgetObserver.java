package com.budgetapp.service;

public interface BudgetObserver {
    void onBudgetWarning(int categoryId, double percentageUsed);
    void onBudgetExceeded(int categoryId, double excessAmount);
}