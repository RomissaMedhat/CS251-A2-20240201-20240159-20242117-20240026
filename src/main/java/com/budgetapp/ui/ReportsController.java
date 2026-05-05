package com.budgetapp.ui;

import com.budgetapp.service.AuthService;
import com.budgetapp.service.ReportService;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.StackPane;
import java.time.LocalDate;

public class ReportsController extends BaseController {
    @FXML private DatePicker startDate, endDate;
    @FXML private StackPane chartContainer;
    private int userId;

    @FXML
    public void initialize() {
        userId = AuthService.getInstance().getCurrentUser().get().getUserId();
        startDate.setValue(LocalDate.now().withDayOfMonth(1));
        endDate.setValue(LocalDate.now());
    }

    @FXML
    public void onGenerate() {
        var data = ReportService.getInstance().getExpenseByCategory(userId, startDate.getValue(), endDate.getValue());
        PieChart chart = new PieChart();
        data.forEach((cat, amt) -> chart.getData().add(new PieChart.Data(cat + ": " + amt, amt)));
        chartContainer.getChildren().setAll(chart);
    }
}