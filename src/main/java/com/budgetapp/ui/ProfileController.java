package com.budgetapp.ui;

import com.budgetapp.model.User;
import com.budgetapp.service.AuthService;
import com.budgetapp.service.UserService;
import com.budgetapp.util.LanguageUtil;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.Locale;

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
        languageCombo.getItems().addAll("English", "\u0627\u0644\u0639\u0631\u0628\u064A\u0629");
        languageCombo.setValue(currentUser.getLanguage().equals("en") ? "English" : "\u0627\u0644\u0639\u0631\u0628\u064A\u0629");
        budgetAlertsCheck.setSelected(currentUser.isBudgetAlertsEnabled());
        goalRemindersCheck.setSelected(currentUser.isGoalRemindersEnabled());
    }

    @FXML
    public void onSave() {
        currentUser.setName(nameField.getText());
        currentUser.setCurrency(currencyField.getText());
        currentUser.setBudgetAlertsEnabled(budgetAlertsCheck.isSelected());
        currentUser.setGoalRemindersEnabled(goalRemindersCheck.isSelected());

        String selectedLang = languageCombo.getValue();
        String newLangCode = selectedLang.equals("\u0627\u0644\u0639\u0631\u0628\u064A\u0629") ? "ar" : "en";
        String oldLangCode = currentUser.getLanguage();
        boolean languageChanged = !oldLangCode.equals(newLangCode);

        if (languageChanged) {
            currentUser.setLanguage(newLangCode);
            Locale newLocale = newLangCode.equals("ar") ? new Locale("ar") : new Locale("en");
            LanguageUtil.setLocale(newLocale);
        }

        if (UserService.getInstance().updateProfile(currentUser)) {
            statusLabel.setText("Profile updated.");
            if (languageChanged) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Language Changed");
                alert.setHeaderText("Language preference saved to " + selectedLang);
                alert.setContentText("The interface will now reload with the new language.");
                alert.showAndWait();
                loadScreen("/fxml/Profile.fxml");
            } else {
                goHome();
            }
        } else {
            statusLabel.setText("Update failed.");
        }
    }

    @FXML
    public void onHome() {
        goHome();
    }
}