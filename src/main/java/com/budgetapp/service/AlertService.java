package com.budgetapp.service;

import com.budgetapp.dao.NotificationDAO;
import com.budgetapp.model.Notification;
import com.budgetapp.model.User;
import java.time.LocalDateTime;
import java.util.List;

public class AlertService implements BudgetObserver {
    private final NotificationDAO notificationDAO;

    public AlertService() { notificationDAO = new NotificationDAO(); }

    @Override
    public void onBudgetWarning(int categoryId, double percentageUsed) {
        User currentUser = AuthService.getInstance().getCurrentUser().orElse(null);
        if (currentUser == null || !currentUser.isBudgetAlertsEnabled()) return;
        Notification n = new Notification();
        n.setUserId(currentUser.getUserId());
        n.setType("BUDGET_WARNING");
        n.setMessage("You have used " + String.format("%.1f", percentageUsed) + "% of your budget for category " + categoryId);
        n.setTimestamp(LocalDateTime.now());
        n.setRead(false);
        notificationDAO.create(n);
    }

    @Override
    public void onBudgetExceeded(int categoryId, double excessAmount) {
        User currentUser = AuthService.getInstance().getCurrentUser().orElse(null);
        if (currentUser == null || !currentUser.isBudgetAlertsEnabled()) return;
        Notification n = new Notification();
        n.setUserId(currentUser.getUserId());
        n.setType("BUDGET_EXCEEDED");
        n.setMessage("Budget exceeded by " + excessAmount + " for category " + categoryId);
        n.setTimestamp(LocalDateTime.now());
        n.setRead(false);
        notificationDAO.create(n);
    }

    public List<Notification> getUnreadMessages(int userId) {
        return notificationDAO.findByUserAndUnread(userId);
    }

    public void markAsRead(int notificationId) {
        notificationDAO.markAsRead(notificationId);
    }
}