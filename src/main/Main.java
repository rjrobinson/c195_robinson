package main;

import controllers.UserController;
import database.DatabaseError;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class Main extends Application {

    @FXML
    private Button exitField;

    @FXML
    private TextField locationField;

    @FXML
    private Label locationLabel;

    @FXML
    private Button loginButton;

    @FXML
    private static Label loginLabel;

    @FXML
    private TextField passwordField;

    @FXML
    private Label passwordLabel;

    @FXML
    private TextField usernameField;

    @FXML
    private Label usernameLabel;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws java.io.IOException, DatabaseError, SQLException {
//        Locale.setDefault(new Locale("fr"));
        ResourceBundle rb = ResourceBundle.getBundle("support/locale", Locale.getDefault());

        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/views/users/login_page.fxml")), rb);

            primaryStage.setTitle("WGU | SW2 | C195");
            primaryStage.setScene(new Scene(root));
            primaryStage.setResizable(false);

            primaryStage.setOnCloseRequest(event -> {
                event.consume();
                try {
                    logout(primaryStage);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void logout(Stage stage) throws java.io.IOException {
        UserController.logoutHandler(stage);
    }
}
