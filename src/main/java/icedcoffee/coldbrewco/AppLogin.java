package icedcoffee.coldbrewco;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AppLogin extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Cold Brew Corp Login");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
