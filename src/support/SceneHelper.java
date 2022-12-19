package support;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import javafx.scene.control.Alert;

import java.io.IOException;

/**
 * The type Scene helper.
 *
 * @author RJ Robinson
 */
public class SceneHelper {
    private Stage stage;

    public SceneHelper(Stage stage) {
        if (stage == null) {
            stage = new Stage();
        }

        this.stage = stage;
    }

    public void changeScene(String fxmlPath) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static void displayAlert(Alert.AlertType alertType, String message) throws IOException {
        Alert alert = new Alert(alertType, message, ButtonType.OK);
        alert.showAndWait();
    }
}
