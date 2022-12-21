package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import models.Country;

public class CustomerController {
    @FXML
    private Button cancelBtn;

    @FXML
    private ComboBox<?> countryDropdown;

    @FXML
    private TextField customerAddress;

    @FXML
    private TextField customerId;

    @FXML
    private TextField customerName;

    @FXML
    private TextField customerPostalCode;

    @FXML
    private ComboBox<Country> divisionDropdown;

    @FXML
    private Label label;

    @FXML
    private TextField phoneLabel;

    @FXML
    private Button saveBtn;
}
