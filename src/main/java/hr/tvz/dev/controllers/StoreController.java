package hr.tvz.dev.controllers;

import hr.tvz.dev.models.*;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class StoreController {
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField addressTextField;
    @FXML
    private ComboBox<String> storeItemComboBox;
    @FXML
    private TableView<Store> storeTableView;
    @FXML
    private TableColumn<Store, String> nameTableColumn;
    @FXML
    private TableColumn<Store, String> addressTableColumn;
    @FXML
    private TableColumn<Store, String> itemsTableColumn;

    private Optional<List<Store>> stores;
    public void initialize() {
        nameTableColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getName()));
        addressTableColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getWebAddress()));
        itemsTableColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getItemsAsString()));

        List<Category> categories = Category.readCategories();
        List<Item> items = Item.readItems(categories);
        setStores(Optional.of(Store.readStores(items)));
        ObservableList<Store> storeObservableList = FXCollections.observableArrayList(getStores().get());
        storeItemComboBox.setItems(FXCollections.observableList(items.stream().map(Item::getName).toList()));
        storeTableView.setItems(storeObservableList);
    }

    public void factorySearch() {

        String filterStoreName = nameTextField.getText();
        String filterCategory = storeItemComboBox.getValue();
        String filterAddress = addressTextField.getText();

        List<Store> filteredStoreList = getStores().get().stream()
                .filter(f -> f.getName().contains(filterStoreName))
                .filter(f -> f.getWebAddress().contains(filterAddress))
                .filter(f -> f.getItemsAsString().contains(filterCategory))
                .collect(Collectors.toList());

        ObservableList<Store> observableFactoryList =
                FXCollections.observableArrayList(filteredStoreList);

        storeTableView.setItems(observableFactoryList);
    }

    public Optional<List<Store>> getStores() {
        return stores;
    }

    public void setStores(Optional<List<Store>> stores) {
        this.stores = stores;
    }
}
