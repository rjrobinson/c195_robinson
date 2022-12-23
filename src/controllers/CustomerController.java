package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Country;
import models.Customer;
import models.Division;
import support.SceneHelper;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

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

    SceneHelper sceneHelper;

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

    @FXML
    void populateDivisionDropdown(ActionEvent event) {
        try {
            divisionDropdown.setItems(Division.getDivisionsByCountry(countryDropdown.getValue()));
            divisionDropdown.setDisable(false);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void cancelBtnHandler(ActionEvent event) throws IOException {
        ApplicationController.selectedCustomer = null;
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

        sceneHelper = new SceneHelper(stage);
        sceneHelper.changeScene("/views/layout/index.fxml");
    }
}
