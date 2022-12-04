package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/users/login_page.fxml")));

            primaryStage.setTitle("WGU | SW2 | C195");
            primaryStage.setScene(new Scene(root));
            primaryStage.setResizable(false);

            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
