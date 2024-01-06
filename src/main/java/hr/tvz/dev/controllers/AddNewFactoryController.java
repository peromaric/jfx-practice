package hr.tvz.dev.controllers;

import hr.tvz.dev.helpers.HelperFunctions;
import hr.tvz.dev.models.Address;
import hr.tvz.dev.models.Factory;
import hr.tvz.dev.models.Item;
import hr.tvz.dev.utils.Database;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;

public class AddNewFactoryController {
    @FXML
    public TableView<Item> itemTableView;
    @FXML
    public TableColumn<Item, String> itemNameTableColumn;
    @FXML
    public TableColumn<Item, String> itemCategoryTableColumn;
    @FXML
    public TableColumn<Item, String> itemVolumeTableColumn;
    @FXML
    public TableColumn<Item, String> itemSellingPriceTableColumn;
    @FXML
    public TextField factoryNameTextField;
    @FXML
    public TextField factoryHouseNumberTextField;
    @FXML
    public TextField factoryStreetTextField;
    @FXML
    public TextField factoryCityTextField;
    @FXML
    public TextField factoryPostalCodeTextField;

    public void initialize() throws SQLException, IOException {
        itemNameTableColumn.setCellValueFactory(
                item -> new ReadOnlyStringWrapper(item.getValue().getName())
        );
        itemSellingPriceTableColumn.setCellValueFactory(
                item -> new ReadOnlyStringWrapper(item.getValue().getSellingPrice().toString())
        );
        itemCategoryTableColumn.setCellValueFactory(
                item -> new ReadOnlyStringWrapper(item.getValue().getCategory().getDescription())
        );
        itemVolumeTableColumn.setCellValueFactory(
                item -> new ReadOnlyStringWrapper(item.getValue().calculateVolume().toString())
        );

        itemTableView.getSelectionModel().setSelectionMode(
                SelectionMode.MULTIPLE
        );
        itemTableView.setItems(
                FXCollections.observableList(
                        Database.getItems()
                )
        );
    }

    public void addNewFactory() throws SQLException, IOException {
        List<Item> factoryItems = itemTableView.getSelectionModel()
                        .getSelectedItems().stream().toList();

        Address factoryAddress = new Address(
                null, factoryStreetTextField.getText(),
                factoryHouseNumberTextField.getText(),
                factoryCityTextField.getText(),
                Integer.parseInt(factoryPostalCodeTextField.getText())
                );

        Database.insertFactory(new Factory(
                null,
                factoryNameTextField.getText(),
                factoryAddress,
                new HashSet<>(factoryItems)
                )
        );
    }
}
