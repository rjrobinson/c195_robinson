package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import models.Appointment;
import models.Customer;

import java.net.URL;
import java.sql.SQLException;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
        //        TODO: fix this
        //        customerState.setCellValueFactory(new PropertyValueFactory<>("state"));
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
        apptContact.setCellValueFactory(new PropertyValueFactory<>("contactID"));
        apptType.setCellValueFactory(new PropertyValueFactory<>("type"));
        apptStartDate.setCellValueFactory(new PropertyValueFactory<>("getStartDate"));
        apptEndDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        apptStartTime.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        apptEndTime.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        apptCustomer.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        apptUser.setCellValueFactory(new PropertyValueFactory<>("userID"));



    }
}
