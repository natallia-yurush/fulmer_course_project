package by.ny.client.dialog;

import javafx.scene.control.Alert;

public class InformationDialog {

    public static void viewMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }
}
