package hr.tvz.dev.controllers;

import hr.tvz.dev.helpers.HelperFunctions;
import hr.tvz.dev.models.Category;
import hr.tvz.dev.models.Item;
import hr.tvz.dev.models.NamedEntity;
import hr.tvz.dev.models.Store;
import hr.tvz.dev.utils.Database;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class AddNewStoreController {
    @FXML
    private TextField storeNameTextField;
    @FXML
    private TextField storeWebAddressTextField;
    @FXML
    public ListView<String> storeItemsListView;
    public List<Item> allItems;

    public void initialize() throws SQLException, IOException {
        allItems = Database.getItems();
        storeItemsListView.cellFactoryProperty();
        storeItemsListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        storeItemsListView.setItems(
                FXCollections.observableList(allItems.stream().map(NamedEntity::getName).toList())
        );
    }

    public void addNewStore() {
        try {
            List<Item> chosenItems = storeItemsListView.getSelectionModel().getSelectedItems().stream().map(
                    storeItem -> allItems.stream().filter(item -> item.getName().contains(storeItem)).findFirst().get()
            ).toList();
            Store store = new Store(
                    null,
                    storeNameTextField.getText(),
                    storeWebAddressTextField.getText(),
                    new HashSet<>(chosenItems)
            );
            Database.insertStore(store);
            HelperFunctions.showOkAlert();
        } catch (RuntimeException | SQLException | IOException ex) {
            HelperFunctions.showErrorAlert(ex);
        }
    }
}
