package com.budgetapp.ui;

import com.budgetapp.service.AuthService;
import com.budgetapp.service.CategoryService;
import com.budgetapp.service.ReportService;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import java.time.LocalDate;
import java.util.Map;

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
        chartContainer.getChildren().clear();
        Map<Integer, Double> data = ReportService.getInstance()
                .getExpenseByCategory(userId, startDate.getValue(), endDate.getValue());

        PieChart chart = new PieChart();
        if (data == null || data.isEmpty()) {
            System.out.println("No expenses found in this period.");
        } else {
            var categories = CategoryService.getInstance().getUserCategories(userId);
            for (Map.Entry<Integer, Double> entry : data.entrySet()) {
                String catName = categories.stream()
                        .filter(c -> c.getCategoryId() == entry.getKey())
                        .findFirst()
                        .map(c -> c.getName())
                        .orElse("Category " + entry.getKey());
                chart.getData().add(new PieChart.Data(catName + ": " + entry.getValue(), entry.getValue()));
            }
        }
        chartContainer.getChildren().add(chart);
    }

    @FXML
    public void onHome() {
        goHome();
    }
}