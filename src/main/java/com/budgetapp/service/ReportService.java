package com.budgetapp.service;

import com.budgetapp.dao.TransactionDAO;
import com.budgetapp.model.Transaction;
import com.budgetapp.model.TransactionType;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportService {
    private static ReportService instance;
    private final TransactionDAO txDAO;

    private ReportService() { txDAO = new TransactionDAO(); }
    public static synchronized ReportService getInstance() {
        if (instance == null) instance = new ReportService();
        return instance;
    }

    public Map<String, Double> getExpenseByCategory(int userId, LocalDate start, LocalDate end) {
        List<Transaction> txns = txDAO.findByDateRange(userId, start, end);
        Map<String, Double> map = new HashMap<>();
        for (Transaction t : txns) {
            if (t.getType() == TransactionType.EXPENSE) {
                String cat = String.valueOf(t.getCategoryId());
                map.put(cat, map.getOrDefault(cat, 0.0) + t.getAmount());
            }
        }
        return map;
    }
}