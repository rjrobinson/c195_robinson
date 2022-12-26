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

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

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

    @FXML
    public Label formTitleLabel; // this is the label that says "New Appointment" or "Edit Appointment"

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


    public static final ObservableList<String> timeList = FXCollections.observableArrayList();

    public ObservableList<String> populateTimeCombo() {
        for (int i = 0; i < 24; i++) {
            for (int j = 0; j < 60; j += 15) {
                timeList.add(String.format("%02d:%02d:00", i, j));
            }
        }
        return timeList;
    }


    @FXML
    void cancelBtnHandler(ActionEvent event) throws IOException {
        ApplicationController.selectedCustomer = null;
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

        SceneHelper sceneHelper = new SceneHelper(stage);
        sceneHelper.changeScene("/views/layout/index.fxml");
    }

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

        int customerID = Customer.getCustomerID(customerName);
        int userID = User.getUserID(userName);
        int contactID = Contact.getContactID(contactName);

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

            if (startTimeCombo.getValue().compareTo(endTimeCombo.getValue()) > 0) {
                valid = false;
                SceneHelper.displayAlert(Alert.AlertType.ERROR, "Error Please enter a start time that is before the end time.");
            }

            if (startDateCombo.getValue().isAfter(endDateCombo.getValue())) {
                valid = false;
                SceneHelper.displayAlert(Alert.AlertType.ERROR, "Error Please enter a start date that is before the end date.");
            }

            if (startDateCombo.getValue().isEqual(endDateCombo.getValue())) {
                if (startTimeCombo.getValue().compareTo(endTimeCombo.getValue()) > 0) {
                    valid = false;
                    SceneHelper.displayAlert(Alert.AlertType.ERROR, "Error Please enter a start time that is before the end time.");
                }
            }
        } catch (Exception e) {
            valid = false;
            SceneHelper.displayAlert(Alert.AlertType.ERROR, "Unknown Error");
        }


        return valid;
    }
}
