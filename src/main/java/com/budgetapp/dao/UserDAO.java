package com.budgetapp.dao;

import com.budgetapp.model.User;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

public class UserDAO implements GenericDAO<User> {
    @Override
    public int create(User user) {
        String sql = "INSERT INTO users (name, email, password_hash, currency, language) VALUES (?,?,?,?,?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getPasswordHash());
            pstmt.setString(4, user.getCurrency());
            pstmt.setString(5, user.getLanguage());
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) { e.printStackTrace(); }
        return -1;
    }

    @Override
    public Optional<User> read(int id) {
        String sql = "SELECT * FROM users WHERE user_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) return Optional.of(map(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return Optional.empty();
    }

    public Optional<User> findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) return Optional.of(map(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return Optional.empty();
    }

    public boolean updateProfile(User user) {
        String sql = "UPDATE users SET name = ?, currency = ?, language = ?, budget_alerts_enabled = ?, goal_reminders_enabled = ? WHERE user_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getCurrency());
            pstmt.setString(3, user.getLanguage());
            pstmt.setBoolean(4, user.isBudgetAlertsEnabled());
            pstmt.setBoolean(5, user.isGoalRemindersEnabled());
            pstmt.setInt(6, user.getUserId());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    private User map(ResultSet rs) throws SQLException {
        User u = new User();
        u.setUserId(rs.getInt("user_id"));
        u.setName(rs.getString("name"));
        u.setEmail(rs.getString("email"));
        u.setPasswordHash(rs.getString("password_hash"));
        u.setCurrency(rs.getString("currency"));
        u.setLanguage(rs.getString("language"));
        u.setBudgetAlertsEnabled(rs.getBoolean("budget_alerts_enabled"));
        u.setGoalRemindersEnabled(rs.getBoolean("goal_reminders_enabled"));
        u.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        return u;
    }

    @Override public boolean update(User user) { return updateProfile(user); }
    @Override public boolean delete(int id) { return false; }
    @Override public List<User> getAll() { return new ArrayList<>(); }
}