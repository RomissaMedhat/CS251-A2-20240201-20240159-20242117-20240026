package com.budgetapp.service;

import com.budgetapp.dao.TransactionDAO;
import com.budgetapp.model.Transaction;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

public class ExportService {
    private static ExportService instance;
    private final TransactionDAO txDAO;

    private ExportService() { txDAO = new TransactionDAO(); }
    public static synchronized ExportService getInstance() {
        if (instance == null) instance = new ExportService();
        return instance;
    }

    public Path exportTransactionsToCSV(int userId, LocalDate start, LocalDate end) throws IOException {
        List<Transaction> transactions = txDAO.findByDateRange(userId, start, end);
        String fileName = "transactions_" + userId + "_" + System.currentTimeMillis() + ".csv";
        Path filePath = Paths.get(System.getProperty("java.io.tmpdir"), fileName);
        try (FileWriter writer = new FileWriter(filePath.toFile())) {
            writer.write("Date,Type,Amount,CategoryId,Description\n");
            for (Transaction t : transactions) {
                writer.write(String.format("%s,%s,%.2f,%d,%s\n",
                        t.getDate().toLocalDate(),
                        t.getType(),
                        t.getAmount(),
                        t.getCategoryId(),
                        t.getDescription().replace(",", ";")));
            }
        }
        return filePath;
    }
}