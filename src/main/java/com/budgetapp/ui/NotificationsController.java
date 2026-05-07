package com.budgetapp.ui;

import com.budgetapp.service.AlertService;
import com.budgetapp.service.AuthService;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class NotificationsController extends BaseController {
    @FXML private ListView<String> notificationsList;
    private int userId;

    @FXML
    public void initialize() {
        userId = AuthService.getInstance().getCurrentUser().get().getUserId();
        var notifs = ((AlertService) AlertService.getInstance()).getUnreadMessages(userId);
        notificationsList.getItems().setAll(notifs.stream().map(n -> n.getMessage()).toList());
        notifs.forEach(n -> ((AlertService) AlertService.getInstance()).markAsRead(n.getNotificationId()));
    }
}