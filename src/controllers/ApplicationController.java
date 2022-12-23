package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.Appointment;
import models.Customer;
import support.SceneHelper;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class ApplicationController implements Initializable {

    @FXML
    private TableColumn<Appointment, String> apptContact;

    @FXML
    private TableColumn<Appointment, String> apptCustomer;

    @FXML
    private Button apptDeleteBtn;

    @FXML
    private TableColumn<Appointment, String> apptDescription;

    @FXML
    private TableColumn<Appointment, String> apptEndDate;

    @FXML
    private TableColumn<Appointment, String> apptEndTime;

    @FXML
    private TableColumn<Appointment, Integer> apptID;

    @FXML
    private TableColumn<Appointment, String> apptLocation;

    @FXML
    private Button apptNewBtn;

    @FXML
    private TableColumn<Appointment, String> apptStartDate;

    @FXML
    private TableColumn<Appointment, String> apptStartTime;

    @FXML
    private TableView<Appointment> apptTable;

    @FXML
    private TableColumn<Appointment, String> apptTitle;

    @FXML
    private TableColumn<Appointment, String> apptType;

    @FXML
    private TableColumn<Appointment, String> apptUser;

    @FXML
    private TableColumn<Customer, String> customerAddress;

    @FXML
    private TableColumn<Customer, String> customerCreatedAt;

    @FXML
    private TableColumn<Customer, String> customerCreatedBy;

    @FXML
    private Button customerDeleteBtn;

    @FXML
    private TableColumn<Customer, Integer> customerID;

    @FXML
    private TableColumn<Customer, String> customerName;

    @FXML
    private Button customerNewBtn;

    @FXML
    private TableColumn<Customer, String> customerPhoneNumber;

    @FXML
    private AnchorPane customerScenePane;

    @FXML
    private TableColumn<Customer, String> customerState;

    @FXML
    private TableView<Customer> customerTable;

    @FXML
    private Button customerUpdateBtn;

    @FXML
    private TableColumn<Customer, String> customerUpdatedAt;

    @FXML
    private TableColumn<Customer, String> customerUpdatedBy;

    @FXML
    private TableColumn<Customer, String> customerZipcode;

    @FXML
    private Button reportsBtn;

    @FXML
    public static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
    @FXML
    public static ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

    public Stage stage;
    public Scene scene;
    SceneHelper sceneHelper;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sceneHelper = new SceneHelper(stage);

        try {
            allCustomers = Customer.getAllCustomers();
            allAppointments = Appointment.getAllAppointments();

            customerTable.setItems(allCustomers);
            apptTable.setItems(allAppointments);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        customerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerID.setCellValueFactory(new PropertyValueFactory<>("CustomerID"));
        customerAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        customerState.setCellValueFactory(new PropertyValueFactory<>("divisionName"));
        customerZipcode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        customerPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phone"));
        customerCreatedAt.setCellValueFactory(new PropertyValueFactory<>("createDate"));
        customerCreatedBy.setCellValueFactory(new PropertyValueFactory<>("createdBy"));
        customerUpdatedAt.setCellValueFactory(new PropertyValueFactory<>("lastUpdate"));
        customerUpdatedBy.setCellValueFactory(new PropertyValueFactory<>("lastUpdatedBy"));


        apptID.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        apptTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        apptDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        apptLocation.setCellValueFactory(new PropertyValueFactory<>("location"));

        apptContact.setCellValueFactory(new PropertyValueFactory<>("contactName"));

        apptType.setCellValueFactory(new PropertyValueFactory<>("type"));

        apptStartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        apptEndDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        apptStartTime.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        apptEndTime.setCellValueFactory(new PropertyValueFactory<>("endTime"));

        apptCustomer.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        apptUser.setCellValueFactory(new PropertyValueFactory<>("userName"));


    }



    @FXML
    public void newCustomerHandler(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        SceneHelper sceneHelper = new SceneHelper(stage);

        sceneHelper.changeScene("/views/customers/_form.fxml");
    }

    /**
     * The constant selectedCustomer.
     */
    public static Customer selectedCustomer = null;

    /**
     * Customer delete handler.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    @FXML
    void customerDeleteHandler(ActionEvent event) throws IOException {
        selectedCustomer = customerTable.getSelectionModel().getSelectedItem();

        if (Objects.isNull(selectedCustomer)) {
            SceneHelper.displayAlert(Alert.AlertType.ERROR, "Please select a customer to delete.");
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alert: You are about to delete this");
            alert.setContentText("Do you want to delete the selected customer?");

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                Customer.deleteCustomer(selectedCustomer.getCustomerID());
                // refresh table
                try {
                    allCustomers = Customer.getAllCustomers();
                    allAppointments = Appointment.getAllAppointments();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                selectedCustomer = null;
            }
        }
    }


    /**
     * The constant selected_part.
     */
    public static Appointment selectedAppointment = null;

    /**
     * Appointment delete handler.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    @FXML
    void appointmentDeleteHandler(ActionEvent event) throws IOException {
        selectedAppointment = apptTable.getSelectionModel().getSelectedItem();

        if (Objects.isNull(selectedAppointment)) {
            SceneHelper.displayAlert(Alert.AlertType.ERROR, "Please select an appointment to delete.");
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alert: You are about to delete this");
            alert.setContentText("Do you want to delete the selected appointment?");

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                Appointment.deleteAppointment(selectedAppointment.getAppointmentID());
                // refresh table
                try {
                    allAppointments = Appointment.getAllAppointments();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                selectedAppointment = null;
            }
        }
    }


    /**
     * Update Customer handler.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    @FXML
    void updateCustomerHandler(ActionEvent event) throws IOException {
        selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
        // Example of correcting a runtime error by preventing null from being passed
        // to the Controller.
        if (Objects.isNull(selectedCustomer)) {
            SceneHelper.displayAlert(Alert.AlertType.ERROR, "Please select a customer to modify.");
        } else {
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            SceneHelper sceneHelper = new SceneHelper(stage);
            sceneHelper.changeScene("/views/customers/_form.fxml");
        }
    }
    /**
     * logout button handler.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    @FXML
    void logout(ActionEvent event) throws IOException {
        System.exit(0);
    }
}
