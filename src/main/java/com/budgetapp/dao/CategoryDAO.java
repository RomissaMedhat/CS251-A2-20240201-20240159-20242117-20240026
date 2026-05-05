package com.budgetapp.dao;

import com.budgetapp.model.Category;
import java.sql.*;
import java.util.*;

public class CategoryDAO implements GenericDAO<Category> {
    @Override
    public int create(Category cat) {
        String sql = "INSERT INTO categories (user_id, name, is_default) VALUES (?,?,?)";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, cat.getUserId());
            pstmt.setString(2, cat.getName());
            pstmt.setBoolean(3, cat.isDefault());
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next())
                return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public List<Category> getDefaultCategories() {
        String sql = "SELECT * FROM categories WHERE is_default = 1";
        return query(sql);
    }

    public List<Category> getUserCategories(int userId) {
        String sql = "SELECT * FROM categories WHERE user_id = ? OR is_default = 1";
        return query(sql, userId);
    }

    public boolean updateCategory(int categoryId, String newName) {
        String sql = "UPDATE categories SET name = ? WHERE category_id = ? AND is_default = 0";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newName);
            pstmt.setInt(2, categoryId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteCategory(int categoryId) {
        String sql = "DELETE FROM categories WHERE category_id = ? AND is_default = 0";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, categoryId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private List<Category> query(String sql, Object... params) {
        List<Category> list = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++)
                pstmt.setObject(i + 1, params[i]);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Category c = new Category();
                c.setCategoryId(rs.getInt("category_id"));
                c.setUserId(rs.getInt("user_id"));
                c.setName(rs.getString("name"));
                c.setDefault(rs.getBoolean("is_default"));
                list.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Optional<Category> read(int id) {
        return Optional.empty();
    }

    @Override
    public boolean update(Category entity) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public List<Category> getAll() {
        return new ArrayList<>();
    }
}