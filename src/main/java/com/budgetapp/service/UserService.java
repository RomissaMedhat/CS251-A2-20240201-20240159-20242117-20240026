package com.budgetapp.service;

import com.budgetapp.dao.UserDAO;
import com.budgetapp.model.User;

public class UserService {
    private static UserService instance;
    private final UserDAO userDAO;

    private UserService() { userDAO = new UserDAO(); }
    public static synchronized UserService getInstance() {
        if (instance == null) instance = new UserService();
        return instance;
    }

    public boolean updateProfile(User user) {
        return userDAO.updateProfile(user);
    }
}