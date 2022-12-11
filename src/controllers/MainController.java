package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import support.SceneHelper;

import java.util.Locale;
import java.util.ResourceBundle;

public class MainController {

    @FXML
    TextField usernameField;
    @FXML
    TextField passwordField;


    private Stage stage;
    private Scene scene;

    private Parent root;

    SceneHelper sceneHelper;

    // ViewComponents
    @FXML
    private Button logoutButton;

    @FXML
    private AnchorPane loginScenePane;

    @FXML
    public void initialize() {
        sceneHelper = new SceneHelper(stage);
    }

    @FXML
    public void login(ActionEvent e) throws java.io.IOException {
        ResourceBundle rb = ResourceBundle.getBundle("support/locale", Locale.getDefault());

        String username = usernameField.getText();
        String password = passwordField.getText();

        // if username or password are empty, show alert
        if (username.isEmpty() || password.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, rb.getString("error.empty_field"), ButtonType.OK);
            alert.showAndWait();
        } else {
            // if username and password are correct, show main scene
            if (username.equals("test") && password.equals("test")) {
                sceneHelper.changeScene("/views/customers/index.fxml");
            } else {
                // if username and password are incorrect, show alert
                Alert alert = new Alert(Alert.AlertType.ERROR, rb.getString("error.invalid_login"), ButtonType.OK);
                alert.showAndWait();
                sceneHelper.changeScene("/views/users/login_page.fxml");
            }
        }
    }

    @FXML
    public void logout(ActionEvent e) throws java.io.IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("You're about to logout");
        alert.setContentText("Do you want to save before existing");
        if (alert.showAndWait().get() == ButtonType.OK) {
            stage = (Stage) ((javafx.scene.Node) e.getSource()).getScene().getWindow();

            System.out.println("Logout button clicked");
            stage.close();
        }
    }

}
