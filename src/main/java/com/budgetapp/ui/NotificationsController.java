package com.budgetapp.ui;

import com.budgetapp.service.AlertService;
import com.budgetapp.service.AuthService;
import com.budgetapp.model.Notification;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import java.util.List;

public class NotificationsController extends BaseController {
    @FXML private ListView<String> notificationsList;
    private int userId;

    @FXML
    public void initialize() {
        var user = AuthService.getInstance().getCurrentUser();
        if (user.isEmpty()) {
            logout();
            return;
        }
        userId = user.get().getUserId();
        loadNotifications();
    }

    private void loadNotifications() {
        List<Notification> notifs = AlertService.getInstance().getUnreadMessages(userId);
        if (notifs != null) {
            notificationsList.getItems().setAll(notifs.stream().map(Notification::getMessage).toList());
            notifs.forEach(n -> AlertService.getInstance().markAsRead(n.getNotificationId()));
        }
    }

    @FXML
    public void onHome() { goHome(); }
}