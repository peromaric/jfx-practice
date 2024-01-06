package hr.tvz.dev.controllers;

import hr.tvz.dev.helpers.HelperFunctions;
import hr.tvz.dev.models.Category;
import hr.tvz.dev.models.Item;
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

public class AddNewItemController {
    @FXML
    private TextField itemNameTextField;
    @FXML
    private ComboBox<String> itemCategoryComboBox;
    @FXML
    private TextField itemWidthTextField;
    @FXML
    private TextField itemHeightTextField;
    @FXML
    private TextField itemLengthTextField;
    @FXML
    private TextField itemProductionCostTextField;
    @FXML
    private TextField itemSellingPriceTextField;
    private List<Category> categoryList;

    public void initialize() throws SQLException, IOException {
        categoryList = Database.getCategories();
        ObservableList<String> itemCategoriesString = FXCollections.observableList(
                categoryList.stream().map(NamedEntity::getName).toList()
        );
        itemCategoryComboBox.setItems(itemCategoriesString);
    }

    public void addNewItem() {
        try {
            Item item = new Item(
                    null,
                    itemNameTextField.getText(),
                    categoryList.stream().filter(cat -> cat.getName().contentEquals(itemCategoryComboBox.getValue())).findFirst().get(),
                    new BigDecimal(itemWidthTextField.getText()),
                    new BigDecimal(itemHeightTextField.getText()),
                    new BigDecimal(itemLengthTextField.getText()),
                    new BigDecimal(itemProductionCostTextField.getText()),
                    new BigDecimal(itemSellingPriceTextField.getText())
            );
            Database.insertItem(item);
            HelperFunctions.showOkAlert();
        } catch (RuntimeException | SQLException | IOException ex) {
            HelperFunctions.showErrorAlert(ex);
        }
    }
}
