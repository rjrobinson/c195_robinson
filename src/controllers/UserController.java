package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import support.SceneHelper;

import java.time.ZoneId;

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
    void login(ActionEvent event) {

    }

    @FXML
    void logout(ActionEvent event) {

    }

}
