package com.budgetapp.dao;

import com.budgetapp.model.Budget;
import java.sql.*;
import java.util.*;

public class BudgetDAO implements GenericDAO<Budget> {
    @Override
    public int create(Budget b) {
        String sql = "INSERT INTO budgets (user_id, category_id, amount, spent, month, year, alert_threshold) VALUES (?,?,?,?,?,?,?)";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, b.getUserId());
            pstmt.setInt(2, b.getCategoryId());
            pstmt.setDouble(3, b.getAmount());
            pstmt.setDouble(4, b.getSpent());
            pstmt.setInt(5, b.getMonth());
            pstmt.setInt(6, b.getYear());
            pstmt.setInt(7, b.getAlertThreshold());
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next())
                return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public Optional<Budget> findByUserCategoryMonth(int userId, int catId, int month, int year) {
        String sql = "SELECT * FROM budgets WHERE user_id = ? AND category_id = ? AND month = ? AND year = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, catId);
            pstmt.setInt(3, month);
            pstmt.setInt(4, year);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next())
                return Optional.of(map(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public boolean update(Budget b) {
        String sql = "UPDATE budgets SET spent = ? WHERE budget_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, b.getSpent());
            pstmt.setInt(2, b.getBudgetId());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Budget map(ResultSet rs) throws SQLException {
        Budget b = new Budget();
        b.setBudgetId(rs.getInt("budget_id"));
        b.setUserId(rs.getInt("user_id"));
        b.setCategoryId(rs.getInt("category_id"));
        b.setAmount(rs.getDouble("amount"));
        b.setSpent(rs.getDouble("spent"));
        b.setMonth(rs.getInt("month"));
        b.setYear(rs.getInt("year"));
        b.setAlertThreshold(rs.getInt("alert_threshold"));
        return b;
    }

    @Override
    public Optional<Budget> read(int id) {
        return Optional.empty();
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public List<Budget> getAll() {
        return new ArrayList<>();
    }
}