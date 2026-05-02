package com.budgetapp.dao;

import com.budgetapp.model.Transaction;
import com.budgetapp.model.TransactionType;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class TransactionDAO implements GenericDAO<Transaction> {
    @Override
    public int create(Transaction t) {
        String sql = "INSERT INTO transactions (user_id, amount, type, category_id, description, transaction_date) VALUES (?,?,?,?,?,?)";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, t.getUserId());
            pstmt.setDouble(2, t.getAmount());
            pstmt.setString(3, t.getType().name());
            pstmt.setInt(4, t.getCategoryId());
            pstmt.setString(5, t.getDescription());
            pstmt.setString(6, t.getDate() != null ? t.getDate().toString() : LocalDateTime.now().toString());
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next())
                return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public List<Transaction> findByUserId(int userId) {
        String sql = "SELECT * FROM transactions WHERE user_id = ? ORDER BY transaction_date DESC";
        return query(sql, userId);
    }

    public List<Transaction> findByDateRange(int userId, LocalDate start, LocalDate end) {
        String sql = "SELECT * FROM transactions WHERE user_id = ? AND date(transaction_date) BETWEEN ? AND ? ORDER BY transaction_date DESC";
        return query(sql, userId, start.toString(), end.toString());
    }

    public List<Transaction> findByCategory(int userId, int categoryId, LocalDate start, LocalDate end) {
        String sql = "SELECT * FROM transactions WHERE user_id = ? AND category_id = ? AND date(transaction_date) BETWEEN ? AND ? ORDER BY transaction_date DESC";
        return query(sql, userId, categoryId, start.toString(), end.toString());
    }

    public double getBalance(int userId) {
        String sql = "SELECT SUM(CASE WHEN type='INCOME' THEN amount ELSE -amount END) as balance FROM transactions WHERE user_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next())
                return rs.getDouble("balance");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public double getMonthlyTotal(int userId, TransactionType type, int month, int year) {
        String sql = "SELECT SUM(amount) FROM transactions WHERE user_id = ? AND type = ? AND strftime('%m', transaction_date) = ? AND strftime('%Y', transaction_date) = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setString(2, type.name());
            pstmt.setString(3, String.format("%02d", month));
            pstmt.setString(4, String.valueOf(year));
            ResultSet rs = pstmt.executeQuery();
            if (rs.next())
                return rs.getDouble(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private List<Transaction> query(String sql, Object... params) {
        List<Transaction> list = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++)
                pstmt.setObject(i + 1, params[i]);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Transaction t = new Transaction();
                t.setTransactionId(rs.getInt("transaction_id"));
                t.setUserId(rs.getInt("user_id"));
                t.setAmount(rs.getDouble("amount"));
                t.setType(TransactionType.valueOf(rs.getString("type")));
                t.setCategoryId(rs.getInt("category_id"));
                t.setDescription(rs.getString("description"));
                t.setDate(rs.getTimestamp("transaction_date").toLocalDateTime());
                list.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Optional<Transaction> read(int id) {
        return Optional.empty();
    }

    @Override
    public boolean update(Transaction entity) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public List<Transaction> getAll() {
        return new ArrayList<>();
    }
}