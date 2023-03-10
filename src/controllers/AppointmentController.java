package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.Appointment;
import models.Contact;
import models.Customer;
import models.User;
import support.SceneHelper;
import support.Utility;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

/**
 * The type Appointment controller. This controller is used for both adding and updating appointments.
 */
public class AppointmentController implements Initializable {

    @FXML
    private TextField apptDescription;

    @FXML
    private TextField apptID;

    @FXML
    private TextField apptLocation;

    @FXML
    private TextField apptTitle;

    @FXML
    private TextField apptType;

    @FXML
    private ComboBox<String> contactCombo;

    @FXML
    private ComboBox<String> customerCombo;

    @FXML
    private DatePicker endDateCombo;

    @FXML
    private ComboBox<String> endTimeCombo;

    @FXML
    private Button saveBtn;

    @FXML
    private DatePicker startDateCombo;

    @FXML
    private ComboBox<String> startTimeCombo;

    @FXML
    private ComboBox<String> userCombo;

    /**
     * The Form title label.
     */
    @FXML
    public Label formTitleLabel; // this is the label that says "New Appointment" or "Edit Appointment"


    /**
     * Initialize.
     *
     * @param url            the url
     * @param resourceBundle the resource bundle
     *
     * Handles the Update and Create actions for Appointments.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Appointment selectedAppointment = ApplicationController.selectedAppointment;
        populateTimeCombo();

        try {
            customerCombo.setItems(Customer.getCustomerNames());
            userCombo.setItems(User.getUserNames());
            contactCombo.setItems(Contact.getContactNames());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        startTimeCombo.setItems(timeList);
        endTimeCombo.setItems(timeList);

        if (selectedAppointment == null) {
            //             THIS IS FOR NEW FORM
            apptID.setText("Auto-Generated");
            formTitleLabel.setText("New Appointment");
        } else {
            formTitleLabel.setText("Edit Appointment");
            //             THIS IS FOR EDIT FORM
            apptID.setText(String.valueOf(selectedAppointment.getAppointmentID()));
            apptTitle.setText(selectedAppointment.getTitle());
            apptDescription.setText(selectedAppointment.getDescription());
            apptLocation.setText(selectedAppointment.getLocation());
            apptType.setText(selectedAppointment.getType());

            startDateCombo.setValue(
                    LocalDate.parse(selectedAppointment.getStartDate())
            );

            startTimeCombo.setValue(selectedAppointment.getStartTime());

            endDateCombo.setValue(
                    LocalDate.parse(selectedAppointment.getEndDate())
            );

            endTimeCombo.setValue(selectedAppointment.getEndTime());

            customerCombo.setValue(selectedAppointment.getCustomerName());
            userCombo.setValue(selectedAppointment.getUserName());
            contactCombo.setValue(selectedAppointment.getContactName());

            // TODO: All combo boxes need to have values populated. -- Maybe special queries that reurns list of names?
        }

    }


    /**
     * The constant timeList. Used in the forms where the time is partitioned into 15 minute intervals on a 24hour clock.
     */
    public static final ObservableList<String> timeList = FXCollections.observableArrayList();

    /**
     * Populate time combo observable list.
     *
     * @return the observable list
     */
    public ObservableList<String> populateTimeCombo() {
        for (int i = 0; i < 24; i++) {
            for (int j = 0; j < 60; j += 15) {
                timeList.add(String.format("%02d:%02d:00", i, j));
            }
        }
        return timeList;
    }


    /**
     * Cancel btn handler.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    @FXML
    void cancelBtnHandler(ActionEvent event) throws IOException {
        ApplicationController.selectedCustomer = null;
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

        SceneHelper sceneHelper = new SceneHelper(stage);
        sceneHelper.changeScene("/views/layout/index.fxml");
    }

    /**
     * Save btn handler.
     *
     * @param event the event
     * @throws IOException  the io exception
     * @throws SQLException the sql exception
     *
     * The save button trigger form validation. If the form is valid, the appointment is saved to the database.
     * If the form is invalid, the user is notified of the errors.
     *
     */
    @FXML
    void saveBtnHandler(ActionEvent event) throws IOException, SQLException {
        Boolean valid = validateForm();

        String title = apptTitle.getText();
        String description = apptDescription.getText();
        String location = apptLocation.getText();
        String type = apptType.getText();
        String startDate = startDateCombo.getValue().toString();
        String startTime = startTimeCombo.getValue();
        String endDate = endDateCombo.getValue().toString();
        String endTime = endTimeCombo.getValue();
        String customerName = customerCombo.getValue();
        String userName = userCombo.getValue();
        String contactName = contactCombo.getValue();

        int customerID = Customer.find(customerName).getCustomerID();
        int userID = User.find(userName).getUserID();
        int contactID = Contact.find(contactName).getContactID();

        if (valid) {
            if (ApplicationController.selectedAppointment == null) {
                Appointment.createAppointment(title, description, location, type, startDate, startTime, endDate, endTime, customerID, userID, contactID);
            } else {
                int appointmentID = Integer.parseInt(apptID.getText());
                Appointment.updateAppointment(appointmentID, title, description, location, type, startDate, startTime, endDate, endTime, customerID, userID, contactID);
            }

            ApplicationController.selectedCustomer = null;
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

            SceneHelper sceneHelper = new SceneHelper(stage);
            sceneHelper.changeScene("/views/layout/index.fxml");
        } else {
            SceneHelper.displayAlert(Alert.AlertType.ERROR, "Something went wrong.");
        }
    }

    /**
     * Validate form boolean.
     *
     * @return the boolean
     * @throws IOException the io exception
     *
     * This form validates the form. It checks for empty fields, and checks for overlapping appointments.
     * This validator also checks to ensure that the times are within business hours.
     */
    public Boolean validateForm() throws IOException {
        Boolean valid = true;
        try {
            if (apptTitle.getText().isEmpty()) {
                valid = false;
                SceneHelper.displayAlert(Alert.AlertType.ERROR, "Error Please enter a title.");
            }

            if (apptDescription.getText().isEmpty()) {
                valid = false;
                SceneHelper.displayAlert(Alert.AlertType.ERROR, "Error Please enter a description.");
            }

            if (apptLocation.getText().isEmpty()) {
                valid = false;
                SceneHelper.displayAlert(Alert.AlertType.ERROR, "Error Please enter a location.");
            }

            if (apptType.getText().isEmpty()) {
                valid = false;
                SceneHelper.displayAlert(Alert.AlertType.ERROR, "Error Please enter a type.");
            }

            if (startDateCombo.getValue() == null) {
                valid = false;
                SceneHelper.displayAlert(Alert.AlertType.ERROR, "Error Please enter a start date.");
            }

            if (startTimeCombo.getValue() == null) {
                valid = false;
                SceneHelper.displayAlert(Alert.AlertType.ERROR, "Error Please enter a start time.");
            }

            if (endDateCombo.getValue() == null) {
                valid = false;
                SceneHelper.displayAlert(Alert.AlertType.ERROR, "Error Please enter an end date.");
            }

            if (endTimeCombo.getValue() == null) {
                valid = false;
                SceneHelper.displayAlert(Alert.AlertType.ERROR, "Error Please enter an end time.");
            }

            if (customerCombo.getValue() == null) {
                valid = false;
                SceneHelper.displayAlert(Alert.AlertType.ERROR, "Error Please enter a customer.");
            }

            if (userCombo.getValue() == null) {
                valid = false;
                SceneHelper.displayAlert(Alert.AlertType.ERROR, "Error Please enter a user.");
            }

            if (contactCombo.getValue() == null) {
                valid = false;
                SceneHelper.displayAlert(Alert.AlertType.ERROR, "Error Please enter a contact.");
            }

            LocalDateTime startDateTime = LocalDateTime.parse(startDateCombo.getValue() + "T" + startTimeCombo.getValue());
            LocalDateTime endDateTime = LocalDateTime.parse(endDateCombo.getValue() + "T" + endTimeCombo.getValue());

            System.out.println(startDateTime);
            System.out.println(endDateTime);

            if (startDateTime.isAfter(endDateTime)) {
                valid = false;
                SceneHelper.displayAlert(Alert.AlertType.ERROR, "Error Start date/time must be before end date/time.");
            }

            if (startDateTime.isEqual(endDateTime)) {
                valid = false;
                SceneHelper.displayAlert(Alert.AlertType.ERROR, "Error Start date/time must be before end date/time.");
            }

            if (!Utility.inBusinessHours(startDateTime, endDateTime)) {
                System.out.println("Validating business hours");
                valid = false;
                SceneHelper.displayAlert(Alert.AlertType.ERROR, "Error Please enter a start time that is between 8:00 AM and 10:00 PM.");
            }
            String apptIDString = apptID.getText();
            if (apptIDString.equals("Auto-Generated")) {
                apptIDString = "0";
            }

            Customer customer = Customer.find(customerCombo.getValue());

            assert customer != null;
            if (Boolean.TRUE.equals(Appointment.appointmentOverlaps(startDateTime, endDateTime, apptIDString, customer.getCustomerID()))) {
                valid = false;
                SceneHelper.displayAlert(Alert.AlertType.ERROR, "Error Please enter a start time that does not overlap with another appointment.");
            }

        } catch (Exception e) {
            valid = false;
            System.out.println(e.getMessage());
            SceneHelper.displayAlert(Alert.AlertType.ERROR, "Unknown Error");
        }


        return valid;
    }

}
