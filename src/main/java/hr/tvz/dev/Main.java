package hr.tvz.dev;

import hr.tvz.dev.threads.SortingItemsThread;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main extends Application {

    public static Stage mainStage;
    public static Stage errorStage;

    @Override
    public void start(Stage stage) throws IOException {

        mainStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("menu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setScene(scene);
        stage.show();

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new SortingItemsThread());
    }

    public static Stage getMainStage() {
        return mainStage;
    }
    public static Stage getErrorStage() { return errorStage; }

    public static void main(String[] args) {
        launch();
    }
}