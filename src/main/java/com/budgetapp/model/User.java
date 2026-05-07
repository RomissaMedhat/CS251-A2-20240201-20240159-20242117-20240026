package com.budgetapp.model;

import java.time.LocalDateTime;

public class User {
    private int userId;
    private String name;
    private String email;
    private String passwordHash;
    private String currency;
    private String language;
    private boolean budgetAlertsEnabled;
    private boolean goalRemindersEnabled;
    private LocalDateTime createdAt;

    public User() {
        this.currency = "EGP";
        this.language = "en";
        this.budgetAlertsEnabled = true;
        this.goalRemindersEnabled = true;
    }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }
    public boolean isBudgetAlertsEnabled() { return budgetAlertsEnabled; }
    public void setBudgetAlertsEnabled(boolean budgetAlertsEnabled) { this.budgetAlertsEnabled = budgetAlertsEnabled; }
    public boolean isGoalRemindersEnabled() { return goalRemindersEnabled; }
    public void setGoalRemindersEnabled(boolean goalRemindersEnabled) { this.goalRemindersEnabled = goalRemindersEnabled; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}