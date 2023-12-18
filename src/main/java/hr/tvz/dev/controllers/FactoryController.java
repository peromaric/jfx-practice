package hr.tvz.dev.controllers;

import hr.tvz.dev.models.*;
import hr.tvz.dev.utils.Database;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FactoryController {
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField addressTextField;
    @FXML
    private ComboBox<String> factoryItemComboBox;
    @FXML
    private TableView<Factory> factoryTableView;
    @FXML
    private TableColumn<Factory, String> nameTableColumn;
    @FXML
    private TableColumn<Factory, String> addressTableColumn;
    @FXML
    private TableColumn<Factory, String> itemsTableColumn;

    private Optional<List<Factory>> factories;
    public void initialize() throws SQLException, IOException {
        nameTableColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getName()));
        addressTableColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getAddress().toString()));
        itemsTableColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getItemsAsString()));

        List<Category> categories = Database.getCategories();
        List<Address> addresses = Database.getAddresses();
        List<Item> items = Database.getItems();
        setFactories(Optional.of(Database.getFactories()));
        ObservableList<Factory> factoryObservableList = FXCollections.observableArrayList(getFactories().get());
        factoryItemComboBox.setItems(FXCollections.observableList(items.stream().map(Item::getName).toList()));
        factoryTableView.setItems(factoryObservableList);
    }

    public void factorySearch() {
        String filterFactoryName = nameTextField.getText();
        String filterCategory = factoryItemComboBox.getValue();
        String filterAddress = addressTextField.getText();

        List<Factory> filteredFactoryList = getFactories().get().stream()
                .filter(f -> f.getName().contains(filterFactoryName))
                .filter(f -> f.getAddress().toString().contains(filterAddress))
                .filter(f -> f.getItemsAsString().contains(filterCategory))
                .collect(Collectors.toList());

        ObservableList<Factory> observableFactoryList =
                FXCollections.observableArrayList(filteredFactoryList);

        factoryTableView.setItems(observableFactoryList);
    }

    public Optional<List<Factory>> getFactories() {
        return factories;
    }

    public void setFactories(Optional<List<Factory>> factories) {
        this.factories = factories;
    }
}
