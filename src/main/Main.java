package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/LoginPage.fxml"));
//         fixme: change this to a proper title later
        primaryStage.setTitle("WGU | SW2 | C195");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }
}
