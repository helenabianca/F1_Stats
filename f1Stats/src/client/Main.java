package client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/main.fxml"));
        Parent root = loader.load();
        stage.setTitle("F1 client.Stats Application");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void main(String[] args) {Application.launch();
    }
}