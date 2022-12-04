package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class MainController {

    private Stage stage;
    private Scene scene;

    private Parent root;
    @FXML
    public void requestLogin(ActionEvent e) throws java.io.IOException {
        System.out.println("Login button clicked");

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/customers/index.fxml")));
        stage = (Stage) ((javafx.scene.Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
