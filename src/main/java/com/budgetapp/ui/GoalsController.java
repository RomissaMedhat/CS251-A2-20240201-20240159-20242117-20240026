package com.budgetapp.ui;

import com.budgetapp.service.AuthService;
import com.budgetapp.service.GoalService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.time.LocalDate;

public class GoalsController extends BaseController {
    @FXML private TextField goalNameField, targetField;
    @FXML private DatePicker deadlinePicker;
    @FXML private ListView<String> goalsList;
    @FXML private Label statusLabel;

    private int userId;

    @FXML
    public void initialize() {
        userId = AuthService.getInstance().getCurrentUser().get().getUserId();
        refreshGoals();
    }

    @FXML
    public void onCreateGoal() {
        try {
            String name = goalNameField.getText();
            double target = Double.parseDouble(targetField.getText());
            LocalDate deadline = deadlinePicker.getValue();
            boolean ok = GoalService.getInstance().createGoal(userId, name, target, 0, deadline);
            if (ok) {
                statusLabel.setText("Goal created.");
                refreshGoals();
            } else {
                statusLabel.setText("Invalid goal data.");
            }
        } catch (Exception e) {
            statusLabel.setText("Error: " + e.getMessage());
        }
    }

    @FXML
    public void onHome() { goHome(); }

    private void refreshGoals() {
        var goals = GoalService.getInstance().getGoalsForUser(userId);
        goalsList.getItems().setAll(
                goals.stream().map(g -> g.getName() + " - " + String.format("%.1f", g.calculateProgress()) + "%").toList()
        );
    }
}