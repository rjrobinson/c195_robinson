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

    /**
     * Instantiates a new Scene helper.
     *
     * @param stage the stage
     */
    public SceneHelper(Stage stage) {
        if (stage == null) {
            stage = new Stage();
        }

        this.stage = stage;
    }

    /**
     * Change scene.
     *
     * @param fxmlPath the fxml path
     * @throws IOException the io exception
     */
    public void changeScene(String fxmlPath) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Display alert.
     *
     * @param alertType the alert type
     * @param message   the message
     * @throws IOException the io exception
     *                    This method is used to display an alert
     *
     */
    public static void displayAlert(Alert.AlertType alertType, String message) throws IOException {
        Alert alert = new Alert(alertType, message, ButtonType.OK);
        alert.showAndWait();
    }
}
