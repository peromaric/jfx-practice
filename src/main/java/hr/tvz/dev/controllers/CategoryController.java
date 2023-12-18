package hr.tvz.dev.controllers;

import hr.tvz.dev.models.Category;
import hr.tvz.dev.models.Factory;
import hr.tvz.dev.models.Store;
import hr.tvz.dev.utils.Database;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CategoryController {
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField descriptionTextField;
    @FXML
    private TableView<Category> categoryTableView;
    @FXML
    private TableColumn<Category, String> categoryIdTableColumn;
    @FXML
    private TableColumn<Category, String> categoryNameTableColumn;
    @FXML
    private TableColumn<Category, String> categoryDescriptionTableColumn;
    Optional<ObservableList<Category>> categories;

    public void initialize() throws SQLException, IOException {
        categoryIdTableColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getId().toString()));
        categoryNameTableColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getName()));
        categoryDescriptionTableColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getDescription()));
        this.categories = Optional.of(FXCollections.observableList(
                Database.getCategories()
        ));
        categoryTableView.setItems(this.categories.get());
    }

    public void categorySearch() {
        String filterCategoryName = nameTextField.getText();
        String filterCategoryDescription = descriptionTextField.getText();

        List<Category> filteredCategories = this.categories.get().stream()
                .filter(c -> c.getName().contains(filterCategoryName))
                .filter(f -> f.getDescription().toString().contains(filterCategoryDescription))
                .collect(Collectors.toList());

        ObservableList<Category> filteredObservableCategories =
                FXCollections.observableArrayList(filteredCategories);

        categoryTableView.setItems(filteredObservableCategories);
    }
}
