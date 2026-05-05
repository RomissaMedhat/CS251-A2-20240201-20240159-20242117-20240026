package com.budgetapp.service;

import com.budgetapp.dao.CategoryDAO;
import com.budgetapp.model.Category;
import java.util.List;

public class CategoryService {
    private static CategoryService instance;
    private final CategoryDAO categoryDAO;

    private CategoryService() { categoryDAO = new CategoryDAO(); }
    public static synchronized CategoryService getInstance() {
        if (instance == null) instance = new CategoryService();
        return instance;
    }

    public List<Category> getDefaultCategories() { return categoryDAO.getDefaultCategories(); }
    public List<Category> getUserCategories(int userId) { return categoryDAO.getUserCategories(userId); }
    public boolean addCustomCategory(int userId, String name) {
        Category c = new Category();
        c.setUserId(userId);
        c.setName(name);
        c.setDefault(false);
        return categoryDAO.create(c) > 0;
    }
    public boolean editCategory(int categoryId, String newName) { return categoryDAO.updateCategory(categoryId, newName); }
    public boolean deleteCategory(int categoryId) { return categoryDAO.deleteCategory(categoryId); }
}