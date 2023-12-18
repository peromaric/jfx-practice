package hr.tvz.dev.helpers;

import javafx.scene.control.Alert;

public class HelperFunctions {
    public static void showErrorAlert(Exception ex) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Exception!");
        alert.setHeaderText("There was a problem with your input");
        alert.setContentText(ex.getMessage());
        alert.showAndWait();
    }
    public static void showOkAlert() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Success!");
        alert.setHeaderText("The operation completed successfully");
        alert.showAndWait();
    }
}
