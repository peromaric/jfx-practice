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
