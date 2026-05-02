package com.budgetapp.dao;

import com.budgetapp.model.Notification;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

public class NotificationDAO implements GenericDAO<Notification> {
    @Override
    public int create(Notification n) {
        String sql = "INSERT INTO notifications (user_id, type, message, is_read, created_at) VALUES (?,?,?,?,?)";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, n.getUserId());
            pstmt.setString(2, n.getType());
            pstmt.setString(3, n.getMessage());
            pstmt.setBoolean(4, n.isRead());
            pstmt.setString(5, n.getTimestamp() != null ? n.getTimestamp().toString() : LocalDateTime.now().toString());
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next())
                return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public List<Notification> findByUserAndUnread(int userId) {
        List<Notification> list = new ArrayList<>();
        String sql = "SELECT * FROM notifications WHERE user_id = ? AND is_read = 0 ORDER BY created_at DESC";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next())
                list.add(map(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean markAsRead(int notificationId) {
        String sql = "UPDATE notifications SET is_read = 1 WHERE notification_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, notificationId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Notification map(ResultSet rs) throws SQLException {
        Notification n = new Notification();
        n.setNotificationId(rs.getInt("notification_id"));
        n.setUserId(rs.getInt("user_id"));
        n.setType(rs.getString("type"));
        n.setMessage(rs.getString("message"));
        n.setRead(rs.getBoolean("is_read"));
        n.setTimestamp(rs.getTimestamp("created_at").toLocalDateTime());
        return n;
    }

    @Override
    public Optional<Notification> read(int id) {
        return Optional.empty();
    }

    @Override
    public boolean update(Notification entity) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public List<Notification> getAll() {
        return new ArrayList<>();
    }
}