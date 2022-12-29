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
import main.Main;
import models.Appointment;
import models.AppointmentReport;
import models.Contact;
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
    private TabPane tab;
    @FXML
    private Tab allApptTab;
    // By Week Table Items
    @FXML
    private TableColumn<Appointment, String> byWeekApptContact;

    @FXML
    private TableColumn<Appointment, String> byWeekApptCustomer;

    @FXML
    private TableColumn<Appointment, String> byWeekApptDescription;

    @FXML
    private TableColumn<Appointment, String> byWeekApptEndDate;

    @FXML
    private TableColumn<Appointment, String> byWeekApptEndTime;

    @FXML
    private TableColumn<Appointment, String> byWeekApptID;

    @FXML
    private TableColumn<Appointment, String> byWeekApptLocation;

    @FXML
    private TableColumn<Appointment, String> byWeekApptStartDate;

    @FXML
    private TableColumn<Appointment, String> byWeekApptStartTime;

    @FXML
    private TableColumn<Appointment, String> byWeekApptTitle;

    @FXML
    private TableColumn<Appointment, String> byWeekApptType;

    @FXML
    private TableColumn<Appointment, String> byWeekApptUser;

    @FXML
    private Tab byWeekTab;

    @FXML
    private TableView<Appointment> apptTableByMonth;

    @FXML
    private TableView<Appointment> apptTableByWeek;

    // By Month Table Items
    @FXML
    private TableColumn<Appointment, String> byMonthApptContact;

    @FXML
    private TableColumn<Appointment, String> byMonthApptCustomer;

    @FXML
    private TableColumn<Appointment, String> byMonthApptDescription;

    @FXML
    private TableColumn<Appointment, String> byMonthApptEndDate;

    @FXML
    private TableColumn<Appointment, String> byMonthApptEndTime;

    @FXML
    private TableColumn<Appointment, String> byMonthApptID;

    @FXML
    private TableColumn<Appointment, String> byMonthApptLocation;

    @FXML
    private TableColumn<Appointment, String> byMonthApptStartDate;

    @FXML
    private TableColumn<Appointment, String> byMonthApptStartTime;

    @FXML
    private TableColumn<Appointment, String> byMonthApptTitle;

    @FXML
    private TableColumn<Appointment, String> byMonthApptType;

    @FXML
    private TableColumn<Appointment, String> byMonthApptUser;

    @FXML
    private Tab byMonthTab;

//     All other items

    @FXML
    private TableColumn<Appointment, String> apptContact;

    @FXML
    private TableColumn<Appointment, String> apptCustomer;

    @FXML
    private Button apptDeleteBtn;

    @FXML
    private Button apptUpdateBtn;
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

    @FXML
    public static ObservableList<Appointment> apptByContact = FXCollections.observableArrayList();

    @FXML
    public static ObservableList<Appointment> byWeekAppointments = FXCollections.observableArrayList();
    @FXML
    public static ObservableList<Appointment> byMonthAppointments = FXCollections.observableArrayList();
    @FXML
    public static ObservableList<AppointmentReport> report1 = FXCollections.observableArrayList();

    public Stage stage;
    public Scene scene;
    SceneHelper sceneHelper;


    //     Report 1 - Total number of appointments by type
    @FXML
    private TableColumn<AppointmentReport, Integer> reportCount;

    @FXML
    private TableColumn<AppointmentReport, String> reportMonth;

    @FXML
    private TableView<AppointmentReport> reportTable;

    @FXML
    private TableColumn<AppointmentReport, String> reportType;


    //     Report 2 - Contact Schedule
    @FXML
    private TableColumn<Appointment, String> contactScheduleApptID;

    @FXML
    private TableColumn<Appointment, String> contactScheduleCustomer;

    @FXML
    private TableColumn<Appointment, String> contactScheduleDescription;

    @FXML
    private TableColumn<Appointment, String> contactScheduleEndDate;

    @FXML
    private TableColumn<Appointment, String> contactScheduleEndTime;

    @FXML
    private TableColumn<Appointment, String> contactScheduleStartDate;

    @FXML
    private TableColumn<Appointment, String> contactScheduleStartTime;

    @FXML
    private TableView<Appointment> contactScheduleTable;

    @FXML
    private TableColumn<Appointment, String> contactScheduleTitle;

    @FXML
    private TableColumn<Appointment, String> contactScheduleType;

    @FXML
    private ComboBox<String> contactDropdown;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sceneHelper = new SceneHelper(stage);

        if (Main.fresh) {
            try {
                Appointment.checkForSoonAppointments(Main.getCurrentUser().getUserID());
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            Main.fresh = false;
        }

        try {
            allCustomers = Customer.getAllCustomers();
            allAppointments = Appointment.getAllAppointments();
            byWeekAppointments = Appointment.getAppointmentsByWeek();
            byMonthAppointments = Appointment.getAppointmentsByMonth();

            Contact contact = Contact.find(1);

            apptByContact = Appointment.getApptByContact(contact.getContactID());

            contactDropdown.setItems(Contact.getContactNames());
            contactDropdown.setValue(contact.getContactName());

            // Report 1
            report1 = AppointmentReport.getAppointmentReport();


            customerTable.setItems(allCustomers);
            apptTable.setItems(allAppointments);
            apptTableByWeek.setItems(byWeekAppointments);
            apptTableByMonth.setItems(byMonthAppointments);
            reportTable.setItems(report1);
            contactScheduleTable.setItems(apptByContact);

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


        byWeekApptID.setCellValueFactory(new PropertyValueFactory<>("period"));
        byWeekApptTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        byWeekApptDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        byWeekApptLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        byWeekApptContact.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        byWeekApptType.setCellValueFactory(new PropertyValueFactory<>("type"));
        byWeekApptStartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        byWeekApptEndDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        byWeekApptStartTime.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        byWeekApptEndTime.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        byWeekApptCustomer.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        byWeekApptUser.setCellValueFactory(new PropertyValueFactory<>("userName"));

        byMonthApptID.setCellValueFactory(new PropertyValueFactory<>("period"));
        byMonthApptTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        byMonthApptDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        byMonthApptLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        byMonthApptContact.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        byMonthApptType.setCellValueFactory(new PropertyValueFactory<>("type"));
        byMonthApptStartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        byMonthApptEndDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        byMonthApptStartTime.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        byMonthApptEndTime.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        byMonthApptCustomer.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        byMonthApptUser.setCellValueFactory(new PropertyValueFactory<>("userName"));

        reportCount.setCellValueFactory(new PropertyValueFactory<>("count"));
        reportMonth.setCellValueFactory(new PropertyValueFactory<>("month"));
        reportType.setCellValueFactory(new PropertyValueFactory<>("type"));

        contactScheduleApptID.setCellValueFactory(new PropertyValueFactory<>("AppointmentID"));
        contactScheduleTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        contactScheduleType.setCellValueFactory(new PropertyValueFactory<>("type"));
        contactScheduleDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        contactScheduleStartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        contactScheduleStartTime.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        contactScheduleEndDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        contactScheduleEndTime.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        contactScheduleCustomer.setCellValueFactory(new PropertyValueFactory<>("customerName"));

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
            alert.setContentText("Do you want to delete the selected appointment with an ID: " + selectedAppointment.getAppointmentID() + "?");

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
     * Update Appt handler.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    @FXML
    void updateApptHandler(ActionEvent event) throws IOException {
        selectedAppointment = apptTable.getSelectionModel().getSelectedItem();

        if (Objects.isNull(selectedAppointment)) {
            SceneHelper.displayAlert(Alert.AlertType.ERROR, "Please select a appointment to modify.");
        } else {
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            SceneHelper sceneHelper = new SceneHelper(stage);
            sceneHelper.changeScene("/views/appointments/_form.fxml");
        }
    }


    /**
     * New Appt handler.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    @FXML
    void newApptHandler(ActionEvent event) throws IOException {
        selectedAppointment = null;
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        SceneHelper sceneHelper = new SceneHelper(stage);
        sceneHelper.changeScene("/views/appointments/_form.fxml");

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

    @FXML
    void setContactSchedule(ActionEvent event) throws IOException, SQLException {
        Contact contact = Contact.find(contactDropdown.getValue());

        apptByContact = Appointment.getApptByContact(contact.getContactID());
    }
}
