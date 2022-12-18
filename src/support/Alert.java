package support;

/**
 * The type Alert.
 */
public class Alert {
    /**
     * Alert.
     *
     * @param message the message that will be sent to the alert
     * @author Robert J. Robisnon
     */
    public static void Alert(String message) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle("Alert");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
