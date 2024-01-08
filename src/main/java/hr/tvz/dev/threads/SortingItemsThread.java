package hr.tvz.dev.threads;

import hr.tvz.dev.Main;
import hr.tvz.dev.models.Item;
import hr.tvz.dev.utils.Database;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;

import static hr.tvz.dev.Main.mainStage;

public class SortingItemsThread implements Runnable {
    private final Logger logger = LoggerFactory.getLogger(SortingItemsThread.class);

    @Override
    public void run() {
        Database database = Database.getInstance();
        while (true) {
            try {
                List<Item> items = database.getItems();
                items.sort(Comparator.comparing(Item::getSellingPrice));
                Item mostExpensive = items.reversed().getFirst();
                String title = "The most expensive item is " + mostExpensive.getName();
                Platform.runLater(() -> mainStage.setTitle(title));
                Thread.sleep(1000);
            } catch (SQLException | IOException e) {
                logger.error(e.getMessage());
                logger.error(Thread.currentThread().getName() + " couldn't access the database");
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                logger.info("SortingItemsThread interrupted");
                throw new RuntimeException(e);
            }
        }

    }
}
