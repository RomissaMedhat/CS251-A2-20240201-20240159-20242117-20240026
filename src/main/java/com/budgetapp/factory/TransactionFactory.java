package com.budgetapp.factory;

import com.budgetapp.model.Transaction;
import com.budgetapp.model.TransactionType;
import java.time.LocalDateTime;

public class TransactionFactory {
    public static Transaction createExpense(int userId, double amount, int categoryId, String desc) {
        Transaction t = new Transaction();
        t.setUserId(userId);
        t.setAmount(amount);
        t.setType(TransactionType.EXPENSE);
        t.setCategoryId(categoryId);
        t.setDescription(desc);
        t.setDate(LocalDateTime.now());
        return t;
    }

    public static Transaction createIncome(int userId, double amount, int categoryId, String desc) {
        Transaction t = new Transaction();
        t.setUserId(userId);
        t.setAmount(amount);
        t.setType(TransactionType.INCOME);
        t.setCategoryId(categoryId);
        t.setDescription(desc);
        t.setDate(LocalDateTime.now());
        return t;
    }
}