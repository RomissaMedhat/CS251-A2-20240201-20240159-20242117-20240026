package com.budgetapp.model;

import java.time.LocalDateTime;

public class Transaction {
    private int transactionId;
    private int userId;
    private double amount;
    private TransactionType type;
    private int categoryId;
    private String description;
    private LocalDateTime date;
    private String paymentMethod;

    public Transaction(double amount, int categoryId, LocalDateTime date, String description, String paymentMethod, int transactionId, TransactionType type, int userId) {
        this.amount = amount;
        this.categoryId = categoryId;
        this.date = date;
        this.description = description;
        this.paymentMethod = paymentMethod;
        this.transactionId = transactionId;
        this.type = type;
        this.userId = userId;
    }

    public int getTransactionId() { return transactionId; }
    public void setTransactionId(int transactionId) { this.transactionId = transactionId; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    public TransactionType getType() { return type; }
    public void setType(TransactionType type) { this.type = type; }
    public int getCategoryId() { return categoryId; }
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }
    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
}