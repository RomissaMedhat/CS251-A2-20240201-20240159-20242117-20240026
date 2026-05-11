package com.budgetapp.ui;

import com.budgetapp.service.AuthService;
import com.budgetapp.service.CategoryService;
import com.budgetapp.model.Category;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.List;

public class ManageCategoriesController extends BaseController {
    @FXML private ListView<String> categoryList;
    @FXML private TextField newCategoryName;
    @FXML private Label statusLabel;

    private int userId;
    private List<Category> categories;

    @FXML
    public void initialize() {
        userId = AuthService.getInstance().getCurrentUser().get().getUserId();
        refreshList();
        categoryList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    @FXML
    public void onAddCategory() {
        String name = newCategoryName.getText().trim();
        if (name.isEmpty()) {
            statusLabel.setText("Category name cannot be empty.");
            return;
        }
        if (CategoryService.getInstance().addCustomCategory(userId, name)) {
            refreshList();
            newCategoryName.clear();
            statusLabel.setText("Category added.");
        } else {
            statusLabel.setText("Failed to add category.");
        }
    }

    @FXML
    public void onEditCategory() {
        int selectedIdx = categoryList.getSelectionModel().getSelectedIndex();
        if (selectedIdx == -1) {
            statusLabel.setText("Select a category to edit.");
            return;
        }
        Category cat = categories.get(selectedIdx);
        if (cat.isDefault()) {
            statusLabel.setText("Cannot edit default categories.");
            return;
        }
        TextInputDialog dialog = new TextInputDialog(cat.getName());
        dialog.setTitle("Edit Category");
        dialog.setHeaderText("Edit category: " + cat.getName());
        dialog.showAndWait().ifPresent(newName -> {
            if (CategoryService.getInstance().editCategory(cat.getCategoryId(), newName)) {
                refreshList();
                statusLabel.setText("Category updated.");
            } else {
                statusLabel.setText("Edit failed.");
            }
        });
    }

    @FXML
    public void onDeleteCategory() {
        int selectedIdx = categoryList.getSelectionModel().getSelectedIndex();
        if (selectedIdx == -1) {
            statusLabel.setText("Select a category to delete.");
            return;
        }
        Category cat = categories.get(selectedIdx);
        if (cat.isDefault()) {
            statusLabel.setText("Cannot delete default categories.");
            return;
        }
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                "Delete category '" + cat.getName() + "'? Transactions will be reassigned to 'Food'.",
                ButtonType.YES, ButtonType.NO);
        if (confirm.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
            if (CategoryService.getInstance().deleteCategory(cat.getCategoryId())) {
                refreshList();
                statusLabel.setText("Category deleted.");
            } else {
                statusLabel.setText("Delete failed.");
            }
        }
    }

    private void refreshList() {
        categories = CategoryService.getInstance().getUserCategories(userId);
        categoryList.getItems().setAll(categories.stream().map(Category::getName).toList());
        if (categories.isEmpty()) {
            categoryList.setPlaceholder(new Label("No custom categories. Add one!"));
        }
    }

    @FXML
    public void onHome() {
        goHome();
    }
}