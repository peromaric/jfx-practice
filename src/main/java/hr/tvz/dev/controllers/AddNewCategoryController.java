package hr.tvz.dev.controllers;

import hr.tvz.dev.helpers.HelperFunctions;
import hr.tvz.dev.models.Category;
import hr.tvz.dev.models.NamedEntity;
import hr.tvz.dev.utils.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class AddNewCategoryController {
    @FXML
    private TextField categoryNameTextField;
    @FXML
    private TextField categoryDescriptionTextField;

    public void addNewCategory() {
        try {
            Category Category = new Category(
                    null,
                    categoryNameTextField.getText(),
                    categoryDescriptionTextField.getText()
            );
            Database.insertCategory(Category);
            HelperFunctions.showOkAlert();
        } catch (RuntimeException | SQLException | IOException ex) {
            HelperFunctions.showErrorAlert(ex);
        }
    }
}
