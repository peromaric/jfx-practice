package hr.tvz.dev.controllers;

import hr.tvz.dev.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;

public class MenuController {
    public void showItemSearchScreen() {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("item.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Main.getMainStage().setTitle("Items");
            Main.getMainStage().setScene(scene);
            Main.getMainStage().show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void showFactorySearchScreen() {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("factory.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Main.getMainStage().setTitle("Factories");
            Main.getMainStage().setScene(scene);
            Main.getMainStage().show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void showStoreSearchScreen() {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("store.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Main.getMainStage().setTitle("Stores");
            Main.getMainStage().setScene(scene);
            Main.getMainStage().show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void showAddNewItemScreen() {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("addNewItem.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Main.getMainStage().setTitle("Add new item");
            Main.getMainStage().setScene(scene);
            Main.getMainStage().show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void showAddNewStoreScreen() {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("addNewStore.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Main.getMainStage().setTitle("Add new store");
            Main.getMainStage().setScene(scene);
            Main.getMainStage().show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void showAddNewCategoryScreen() {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("addNewCategory.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Main.getMainStage().setTitle("Add new category");
            Main.getMainStage().setScene(scene);
            Main.getMainStage().show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void showAddNewFactoryScreen() {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("addNewFactory.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Main.getMainStage().setTitle("Add new factory");
            Main.getMainStage().setScene(scene);
            Main.getMainStage().show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void showCategorySearchScreen() {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("category.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Main.getMainStage().setTitle("Add new category");
            Main.getMainStage().setScene(scene);
            Main.getMainStage().show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void showMenuScreen() {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("menu.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Main.getMainStage().setTitle("New");
            Main.getMainStage().setScene(scene);
            Main.getMainStage().show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
