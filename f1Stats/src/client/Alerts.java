package client;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DialogPane;

public class Alerts {
    public static void showAlert(String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Invalid name");
        alert.setHeaderText(null);
        content+="\nEnter the name again and make sure it respects the specified format";
        alert.setContentText(content);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add("resources/alert.css");
        dialogPane.getStyleClass().add("dialog-pane");
        alert.showAndWait();
    }
}
