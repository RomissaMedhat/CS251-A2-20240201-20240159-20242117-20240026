package com.budgetapp.dao;

import com.budgetapp.model.Goal;
import com.budgetapp.model.GoalStatus;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;

public class GoalDAO implements GenericDAO<Goal> {
    @Override
    public int create(Goal g) {
        String sql = "INSERT INTO goals (user_id, name, target_amount, current_amount, deadline, status) VALUES (?,?,?,?,?,?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, g.getUserId());
            pstmt.setString(2, g.getName());
            pstmt.setDouble(3, g.getTargetAmount());
            pstmt.setDouble(4, g.getCurrentAmount());
            pstmt.setString(5, g.getDeadline().toString());
            pstmt.setString(6, g.getStatus().name());
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) { e.printStackTrace(); }
        return -1;
    }

    public List<Goal> findByUserId(int userId) {
        List<Goal> list = new ArrayList<>();
        String sql = "SELECT * FROM goals WHERE user_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) list.add(map(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public boolean updateProgress(int goalId, double newAmount) {
        String sql = "UPDATE goals SET current_amount = ?, status = ? WHERE goal_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, newAmount);
            pstmt.setString(2, newAmount >= getTargetAmount(goalId) ? GoalStatus.COMPLETED.name() : GoalStatus.IN_PROGRESS.name());
            pstmt.setInt(3, goalId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    private double getTargetAmount(int goalId) throws SQLException {
        String sql = "SELECT target_amount FROM goals WHERE goal_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, goalId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) return rs.getDouble(1);
        }
        return 0;
    }

    private Goal map(ResultSet rs) throws SQLException {
        Goal g = new Goal();
        g.setGoalId(rs.getInt("goal_id"));
        g.setUserId(rs.getInt("user_id"));
        g.setName(rs.getString("name"));
        g.setTargetAmount(rs.getDouble("target_amount"));
        g.setCurrentAmount(rs.getDouble("current_amount"));
        g.setDeadline(LocalDate.parse(rs.getString("deadline")));
        g.setStatus(GoalStatus.valueOf(rs.getString("status")));
        return g;
    }

    @Override public Optional<Goal> read(int id) { return Optional.empty(); }
    @Override public boolean update(Goal entity) { return false; }
    @Override public boolean delete(int id) { return false; }
    @Override public List<Goal> getAll() { return new ArrayList<>(); }
}