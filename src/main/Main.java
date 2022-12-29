package main;

import controllers.UserController;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;


/**
 * The type Main.
 */
public class Main extends Application {

    private static User currentUser;

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

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * The constant fresh. Used to help with notifications and alerts on initial login
     */
    public static Boolean fresh = true;
    @Override
    public void start(Stage primaryStage) throws java.io.IOException, SQLException {
        //        Locale.setDefault(new Locale("fr")); TESTING PURPOSES ONLY
        ResourceBundle rb = ResourceBundle.getBundle("support/locale", Locale.getDefault());

        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/views/layout/index.fxml")), rb);

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

    /**
     * Logout.
     *
     * @param stage the stage
     * @throws IOException the io exception
     */
    @FXML
    public void logout(Stage stage) throws java.io.IOException {
        UserController.logoutHandler(stage);
    }

    /**
     * Gets current user.
     *
     * @return the current user
     */
    public static User getCurrentUser() {
        return currentUser;
    }

    /**
     * Sets current user.
     *
     * @param user the user
     */
    public static void setCurrentUser(User user) {
        currentUser = user;
    }
}
