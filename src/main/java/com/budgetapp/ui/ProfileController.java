package com.budgetapp.ui;

import com.budgetapp.model.User;
import com.budgetapp.service.AuthService;
import com.budgetapp.service.UserService;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ProfileController extends BaseController {
    @FXML private TextField nameField, currencyField;
    @FXML private ComboBox<String> languageCombo;
    @FXML private CheckBox budgetAlertsCheck, goalRemindersCheck;
    @FXML private Label statusLabel;

    private User currentUser;

    @FXML
    public void initialize() {
        currentUser = AuthService.getInstance().getCurrentUser().orElseThrow();
        nameField.setText(currentUser.getName());
        currencyField.setText(currentUser.getCurrency());
        languageCombo.getItems().addAll("en", "ar");
        languageCombo.setValue(currentUser.getLanguage());
        budgetAlertsCheck.setSelected(currentUser.isBudgetAlertsEnabled());
        goalRemindersCheck.setSelected(currentUser.isGoalRemindersEnabled());
    }

    @FXML
    public void onSave() {
        currentUser.setName(nameField.getText());
        currentUser.setCurrency(currencyField.getText());
        currentUser.setLanguage(languageCombo.getValue());
        currentUser.setBudgetAlertsEnabled(budgetAlertsCheck.isSelected());
        currentUser.setGoalRemindersEnabled(goalRemindersCheck.isSelected());
        if (UserService.getInstance().updateProfile(currentUser)) {
            statusLabel.setText("Profile updated.");
        } else {
            statusLabel.setText("Update failed.");
        }
    }
}