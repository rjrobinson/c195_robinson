package controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.User;
import support.SceneHelper;

import java.io.IOException;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * The type User controller.
 */
public class UserController {


    @FXML
    private Button exitField;

    @FXML
    private Label locationField;

    @FXML
    private Label locationLabel;

    @FXML
    private Button loginButton;

    @FXML
    private Label loginLabel;

    @FXML
    private TextField passwordField;

    @FXML
    private Label passwordLabel;

    @FXML
    private TextField usernameField;

    @FXML
    private Label usernameLabel;

    private Stage stage;
    private Scene scene;

    private Parent root;

    /**
     * The Scene helper.
     */
    SceneHelper sceneHelper;


    /**
     * Initialize the user controller
     */
    @FXML
    public void initialize() {
        sceneHelper = new SceneHelper(stage);
        // get users current location
        locationField.setText(ZoneId.systemDefault().toString());
    }

    /**
     * Login.
     *
     * @param e the e
     * @throws IOException  the io exception
     * @throws SQLException the sql exception
     *                      <p>
     *                      Login button action
     *                      handles all login logic
     *                      Checks if a user exists in the database and if the provided password matches the one in the database
     *                      If the user exists and the password matches, the user is logged in and the main screen is loaded
     *                      If the user does not exist or the password does not match, an error message is displayed
     *                      There is also a recorded attempt to login in a text file that is handled in the validation method
     */

    @FXML
    public void login(ActionEvent e) throws java.io.IOException, SQLException {
        ResourceBundle rb = ResourceBundle.getBundle("support/locale", Locale.getDefault());

        String username = usernameField.getText();
        String password = passwordField.getText();

        // if username or password are empty, show alert
        if (username.isEmpty() || password.isEmpty()) {
            sceneHelper.displayAlert(Alert.AlertType.ERROR, rb.getString("error.empty_field"));
        } else {
            // if username and password are correct, show main scene
            if (User.validateUser(username, password)) {
                sceneHelper.changeScene("/views/layout/index.fxml");
            } else {
                // if username and password are incorrect, show alert
                sceneHelper.displayAlert(Alert.AlertType.ERROR, rb.getString("error.invalid_login"));
                sceneHelper.changeScene("/views/users/login_page.fxml");
            }
        }
    }

    /**
     * Logout.
     *
     * @param e the e
     * @throws IOException the io exception
     *                     Logout button action
     *                     Logs the user out and returns to the login screen
     *                     The user is logged out by setting the current user to null
     */
    @FXML
    public void logout(ActionEvent e) throws java.io.IOException {
        logoutHandler(stage);
    }

    /**
     * Logout handler.
     *
     * @param stage the stage
     *
     *              Logs the user out and returns to the login screen
     *              The user is logged out by setting the current user to null
     *              The application exits
     */
    public static void logoutHandler(Stage stage) {
        try {
            ResourceBundle rb = ResourceBundle.getBundle("support/locale", Locale.getDefault());
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

            alert.setTitle(rb.getString("logout"));

            alert.setHeaderText(rb.getString("logout.header"));
            alert.setContentText(rb.getString("logout.content"));
            if (alert.showAndWait().get() == ButtonType.OK) {
                System.out.println("Logout button clicked");
                stage.close();
            }
        } catch (Exception e) {
            if (stage != null) {
                stage.close();
            } else {
                Platform.exit();
                System.exit(0);
            }
        }
    }
}
