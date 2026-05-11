package com.budgetapp.ui;

import com.budgetapp.service.AuthService;
import com.budgetapp.service.ExportService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import java.io.File;
import java.nio.file.Files;
import java.time.LocalDate;

public class ExportController extends BaseController {
    @FXML private DatePicker startDate, endDate;
    @FXML private Label statusLabel;
    private int userId;

    @FXML
    public void initialize() {
        userId = AuthService.getInstance().getCurrentUser().get().getUserId();
        startDate.setValue(LocalDate.now().minusMonths(1));
        endDate.setValue(LocalDate.now());
    }

    @FXML
    public void onExport() {
        try {
            var path = ExportService.getInstance().exportTransactionsToCSV(userId, startDate.getValue(), endDate.getValue());
            FileChooser chooser = new FileChooser();
            chooser.setInitialFileName(path.getFileName().toString());
            File saveFile = chooser.showSaveDialog(statusLabel.getScene().getWindow());
            if (saveFile != null) {
                Files.copy(path, saveFile.toPath());
                statusLabel.setText("Exported to " + saveFile.getAbsolutePath());
            }
        } catch (Exception e) {
            statusLabel.setText("Export failed: " + e.getMessage());
        }
    }

    @FXML
    public void onHome() { goHome(); }
}