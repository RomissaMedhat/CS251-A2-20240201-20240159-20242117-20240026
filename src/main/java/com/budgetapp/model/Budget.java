package com.budgetapp.model;

public class Budget {
    private int budgetId;
    private int userId;
    private int categoryId;
    private double amount;
    private double spent;
    private int month;
    private int year;
    private int alertThreshold;

    public int getBudgetId() { return budgetId; }
    public void setBudgetId(int budgetId) { this.budgetId = budgetId; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public int getCategoryId() { return categoryId; }
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    public double getSpent() { return spent; }
    public void setSpent(double spent) { this.spent = spent; }
    public int getMonth() { return month; }
    public void setMonth(int month) { this.month = month; }
    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }
    public int getAlertThreshold() { return alertThreshold; }
    public void setAlertThreshold(int alertThreshold) { this.alertThreshold = alertThreshold; }

    public double calculateRemaining() { return amount - spent; }
    public double getPercentageUsed() { return (amount == 0) ? 0 : (spent / amount) * 100; }
    public boolean isExceeded() { return spent >= amount; }
}