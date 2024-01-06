package hr.tvz.dev.controllers;

import hr.tvz.dev.models.Item;
import hr.tvz.dev.utils.Database;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.Callback;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ItemController {
    @FXML
    private TextField nameTextField;
    @FXML
    private ComboBox<String> itemComboBox;
    @FXML
    private TableView<Item> itemTableView;
    @FXML
    private TableColumn<Item, String> nameTableColumn;
    @FXML
    private TableColumn<Item, String> categoryTableColumn;
    @FXML
    private TableColumn<Item, String> volumeTableColumn;
    @FXML
    private TableColumn<Item, String> sellingPriceTableColumn;
    @FXML
    private TableColumn<Item, String> discountTableColumn;

    public void initialize() throws SQLException, IOException {
        nameTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Item, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Item, String> param) {
                return new ReadOnlyStringWrapper(param.getValue().getName());
            }
        });
        categoryTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Item, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Item, String> param) {
                return new ReadOnlyStringWrapper(param.getValue().getCategory().getName());
            }
        });
        volumeTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Item, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Item, String> param) {
                return new ReadOnlyStringWrapper(param.getValue().calculateVolume().toString());
            }
        });


        sellingPriceTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Item, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Item, String> param) {
                return new ReadOnlyStringWrapper(param.getValue().getSellingPrice().toString());
            }
        });


        List<Item> itemList = Database.getItems();
        ObservableList<String> itemCategoriesString = FXCollections.observableList(
                new ArrayList<>(itemList.stream()
                        .map(
                                item -> item.getCategory().getName()).collect(Collectors.toSet())
                )
        );
        ObservableList<Item> observableItemList =
                FXCollections.observableArrayList(itemList);

        itemComboBox.setItems(itemCategoriesString);
        itemTableView.setItems(observableItemList);
    }

    public void itemSearch() throws SQLException, IOException {
        List<Item> carsList = Database.getItems();

        String filterItemName = nameTextField.getText();
        String filterCategory = itemComboBox.getValue();

        List<Item> filteredItemList = carsList.stream()
                .filter(c -> c.getName().contains(filterItemName))
                .filter(c -> c.getCategory().getName().equals(filterCategory))
                .collect(Collectors.toList());

        ObservableList<Item> observableItemList =
                FXCollections.observableArrayList(filteredItemList);

        itemTableView.setItems(observableItemList);
    }

}
