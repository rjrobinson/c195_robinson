package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.User;
import support.SceneHelper;

import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

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

    SceneHelper sceneHelper;


    @FXML
    public void initialize() {
        sceneHelper = new SceneHelper(stage);

        // get users current location
        locationField.setText(ZoneId.systemDefault().toString());
    }

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
                sceneHelper.changeScene("/views/customers/index.fxml");
            } else {
                // if username and password are incorrect, show alert
                sceneHelper.displayAlert(Alert.AlertType.ERROR, rb.getString("error.invalid_login"));
                sceneHelper.changeScene("/views/users/login_page.fxml");
            }
        }
    }

    @FXML
    public void logout(ActionEvent e) throws java.io.IOException {
        ResourceBundle rb = ResourceBundle.getBundle("support/locale", Locale.getDefault());
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.setTitle(rb.getString("logout"));

        alert.setHeaderText(rb.getString("logout.header"));
        alert.setContentText(rb.getString("logout.content"));
        if (alert.showAndWait().get() == ButtonType.OK) {
            System.out.println("Logout button clicked");
            stage.close();
        }
    }
}
