package models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static models.Base.conn;

public class Appointment {


    private String createDate;
    private String createdBy;
    private String description;
    private String end;
    private String endDate;
    private String endTime;
    private String lastUpdate;
    private String lastUpdateBy;
    private String location;
    private String start;
    private String startDate;
    private String startTime;
    private String title;
    private String type;
    private int appointmentID;
    private String contactName;
    private String customerName;
    private String userName;

    //constructor
    public Appointment(int appointmentID, String title, String description, String location, String type, String start, String end, String createDate, String createdBy, String lastUpdate, String lastUpdateBy, String contactName, String customerName, String userName, String startDate, String startTime, String endDate, String endTime) {
        this.appointmentID = appointmentID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdateBy = lastUpdateBy;
        this.contactName = contactName;
        this.customerName = customerName;
        this.userName = userName;
        this.startDate = startDate;
        this.startTime = startTime;
        this.endDate = endDate;
        this.endTime = endTime;
    }

    static ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

    public static ObservableList<Appointment> getAllAppointments() throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(allApptSql());

        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Appointment appt = new Appointment(
                    rs.getInt("Appointment_ID"),
                    rs.getString("Title"),
                    rs.getString("Description"),
                    rs.getString("Location"),
                    rs.getString("Type"),
                    rs.getString("Start"),
                    rs.getString("End"),
                    rs.getString("Create_Date"),
                    rs.getString("Created_By"),
                    rs.getString("Last_Update"),
                    rs.getString("Last_Updated_By"),
                    rs.getString("Contact_Name"),
                    rs.getString("Customer_Name"),
                    rs.getString("User_Name"),
                    rs.getString("startDate"),
                    rs.getString("startTime"),
                    rs.getString("endDate"),
                    rs.getString("endTime")
            );
            allAppointments.add(appt);
        }
        return allAppointments;
    }


    public static String allApptSql() {
        return """
                 SELECT a.Appointment_ID,
                       c.Customer_Name,
                       u.User_Name,
                       co.Contact_Name,
                       a.Title,
                       a.Description,
                       a.Type,
                       a.Location,
                       a.start,
                       a.end,
                       a.Create_Date,
                       a.Created_By,
                       a.Last_Update,
                       a.Last_Updated_By,
                       date_format(start, '%Y-%m-%d') as `startDate`,
                       date_format(start, '%H:%i:%s') as `startTime`,
                       date_format(end, '%Y-%m-%d') as `endDate`,
                       date_format(end, '%H:%i:%s') as `endTime`
                FROM appointments AS a
                         INNER JOIN customers AS c ON a.Customer_ID = c.Customer_ID
                         INNER JOIN users AS u ON a.User_ID = u.User_ID
                         INNER JOIN contacts AS co ON a.Contact_ID = co.Contact_ID
                                """;
    }

    //getters and setters
    public int getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    public String getStartDate() {
        return start.substring(0, 10);
    }

    public String getContactName() {
        return contactName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getUserName() {
        return userName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }



}
