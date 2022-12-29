package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import main.Main;
import models.Country;
import models.Customer;
import models.Division;
import support.SceneHelper;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * The type Customer controller.
 */
public class CustomerController implements Initializable {
    @FXML
    private Button cancelBtn;

    @FXML
    private ComboBox<String> countryDropdown;

    @FXML
    private TextField customerAddress;

    @FXML
    private TextField customerId;

    @FXML
    private TextField customerName;

    @FXML
    private TextField customerPostalCode;

    @FXML
    private ComboBox<String> divisionDropdown;

    @FXML
    private Label label;

    @FXML
    private TextField phoneLabel;

    @FXML
    private Button saveBtn;

    @FXML
    private Label formTitleLabel;

    /**
     * The Scene helper. A utility class that allows for quick scene changes
     */
    SceneHelper sceneHelper;

    /**
     * Initialize.
     *
     * @param url            the url
     * @param resourceBundle the resource bundle
     *                       <p>
     *                       Handles the Update and Create actions for Customers.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Customer selectedCustomer = ApplicationController.selectedCustomer;
        if (selectedCustomer == null) {
            //             THIS IS FOR NEW FORM
            customerId.setText("Auto-Generated");
            formTitleLabel.setText("Add Customer");
            customerId.setDisable(true);
            divisionDropdown.setDisable(true);
            try {
                countryDropdown.setItems(Country.getAllCountries());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            //             THIS IS FOR EDIT FORM
            customerId.setDisable(true);
            customerId.setText(String.valueOf(selectedCustomer.getCustomerID()));

            formTitleLabel.setText("Edit Customer");
            customerName.setText(ApplicationController.selectedCustomer.getCustomerName());
            customerAddress.setText(ApplicationController.selectedCustomer.getAddress());
            customerPostalCode.setText(ApplicationController.selectedCustomer.getPostalCode());
            phoneLabel.setText(ApplicationController.selectedCustomer.getPhone());
            try {
                countryDropdown.setItems(Country.getAllCountries());
                countryDropdown.setValue(ApplicationController.selectedCustomer.getCountryName());
                divisionDropdown.setItems(Division.getDivisionsByCountry(countryDropdown.getValue()));
                divisionDropdown.setValue(ApplicationController.selectedCustomer.getDivisionName());
                divisionDropdown.setDisable(false);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
    }

    /**
     * Populate division dropdown.
     *
     * @param event the event
     *              Populates the division dropdown based on the selected country
     *              in the country dropdown.
     *              This is a listener for the country dropdown.
     */
    @FXML
    void populateDivisionDropdown(ActionEvent event) {
        try {
            divisionDropdown.setItems(Division.getDivisionsByCountry(countryDropdown.getValue()));
            divisionDropdown.setDisable(false);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Cancel btn handler.
     *
     * @param event the event
     * @throws IOException the io exception
     *                     Handles the cancel button action.
     *                     This will close the form and return to the main screen.
     *                     This is a listener for the cancel button.
     */
    @FXML
    void cancelBtnHandler(ActionEvent event) throws IOException {
        ApplicationController.selectedCustomer = null;
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

        sceneHelper = new SceneHelper(stage);
        sceneHelper.changeScene("/views/layout/index.fxml");
    }

    /**
     * Validate form.
     *
     * @param event the event
     * @throws IOException the io exception
     *                     Validates the form and handles the save button action.
     *                     Provides error messages if the form is not valid.
     */
    void validateForm(ActionEvent event) throws IOException {
        if (customerName.getText().isEmpty()) {
            sceneHelper.displayAlert(Alert.AlertType.ERROR, "Customer Name is required");
        }
        if (customerAddress.getText().isEmpty()) {
            sceneHelper.displayAlert(Alert.AlertType.ERROR, "Customer Address is required");
        }
        if (customerPostalCode.getText().isEmpty()) {
            sceneHelper.displayAlert(Alert.AlertType.ERROR, "Customer Postal Code is required");
        }
        if (phoneLabel.getText().isEmpty()) {
            sceneHelper.displayAlert(Alert.AlertType.ERROR, "Customer Phone is required");
        }
        if (countryDropdown.getValue() == null) {
            sceneHelper.displayAlert(Alert.AlertType.ERROR, "Country is required");
        }
        if (divisionDropdown.getValue() == null) {
            sceneHelper.displayAlert(Alert.AlertType.ERROR, "Customer Division is required");
        }
    }

    /**
     * Save btn handler.
     *
     * @param event the event
     * @throws IOException  the io exception
     * @throws SQLException the sql exception
     *                      Handles the save button action.
     *                      This will save the customer to the database and return to the main screen.
     *                      This is a listener for the save button.
     */
    @FXML
    void saveBtnHandler(ActionEvent event) throws IOException, SQLException {
        validateForm(event);
        if (ApplicationController.selectedCustomer == null) {
            Customer newCustomer = new Customer(
                    customerName.getText(),
                    customerAddress.getText(),
                    customerPostalCode.getText(),
                    phoneLabel.getText(),
                    Main.getCurrentUser().getUserName(),
                    Main.getCurrentUser().getUserName(),
                    divisionDropdown.getValue()
            );
            newCustomer.create();
        } else {
            Customer existingCustomer = new Customer(
                    Integer.parseInt(customerId.getText()),
                    customerName.getText(),
                    customerAddress.getText(),
                    customerPostalCode.getText(),
                    phoneLabel.getText(),
                    Main.getCurrentUser().getUserName(),
                    divisionDropdown.getValue()
            );
            existingCustomer.update();
        }

        ApplicationController.selectedCustomer = null;
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

        sceneHelper = new SceneHelper(stage);
        sceneHelper.changeScene("/views/layout/index.fxml");
    }
}
