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

    public boolean updateNotificationSettings(int userId, boolean budgetAlerts, boolean goalReminders) {
        User user = userDAO.read(userId).orElse(null);
        if (user == null) return false;
        user.setBudgetAlertsEnabled(budgetAlerts);
        user.setGoalRemindersEnabled(goalReminders);
        return userDAO.updateProfile(user);
    }
}