package com.budgetapp.ui;

import com.budgetapp.service.AuthService;
import com.budgetapp.service.CategoryService;
import com.budgetapp.model.Category;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ManageCategoriesController extends BaseController {
    @FXML private ListView<Category> categoryList;
    @FXML private TextField newCategoryName;
    @FXML private Label statusLabel;
    private int userId;

    @FXML
    public void initialize() {
        userId = AuthService.getInstance().getCurrentUser().get().getUserId();
        refreshList();
    }

    @FXML
    public void onAddCategory() {
        String name = newCategoryName.getText().trim();
        if (!name.isEmpty() && CategoryService.getInstance().addCustomCategory(userId, name)) {
            refreshList();
            newCategoryName.clear();
        } else {
            statusLabel.setText("Failed to add category.");
        }
    }

    @FXML
    public void onEditCategory() {
        Category selected = categoryList.getSelectionModel().getSelectedItem();
        if (selected != null && !selected.isDefault()) {
            TextInputDialog dialog = new TextInputDialog(selected.getName());
            dialog.setTitle("Edit Category");
            dialog.showAndWait().ifPresent(newName -> {
                if (CategoryService.getInstance().editCategory(selected.getCategoryId(), newName)) {
                    refreshList();
                }
            });
        }
    }

    @FXML
    public void onDeleteCategory() {
        Category selected = categoryList.getSelectionModel().getSelectedItem();
        if (selected != null && !selected.isDefault()) {
            if (CategoryService.getInstance().deleteCategory(selected.getCategoryId())) {
                refreshList();
            }
        }
    }

    private void refreshList() {
        categoryList.getItems().setAll(CategoryService.getInstance().getUserCategories(userId));
    }
}