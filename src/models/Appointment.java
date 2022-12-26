package models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.Main;
import support.Utility;

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
        allAppointments.clear();
        PreparedStatement stmt = conn.prepareStatement(allApptSql());

        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            String startTime = rs.getString("start");
            String startTimeLocal = Utility.toLocal(startTime);
            String endTime = rs.getString("end");
            String endTimeLocal = Utility.toLocal(endTime);

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
                    startTimeLocal.substring(0, 10),
                    startTimeLocal.substring(11, 16),
                    endTimeLocal.substring(0, 10),
                    endTimeLocal.substring(11, 16)
            );
            allAppointments.add(appt);
        }
        return allAppointments;
    }

    // Database Operations
    public static void deleteAppointment(int appointmentID) {
        try {
            // First - Delete any appointment associated with this customer
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM appointments WHERE Appointment_ID = ?");
            stmt.setInt(1, appointmentID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error deleting appointment: " + e.getMessage());
        }
    }

    public static String allApptSql() {
        return """
                 SELECT    a.appointment_id,
                           c.customer_name,
                           u.user_name,
                           co.contact_name,
                           a.title,
                           a.description,
                           a.type,
                           a.location,
                           date_format(a.start, '%Y-%m-%dT%H:%i:%sZ') as start,
                           date_format(a.end, '%Y-%m-%dT%H:%i:%sZ') as end,
                           a.create_date,
                           a.created_by,
                           a.last_update,
                           a.last_updated_by,
                           date_format(start, '%Y-%m-%d') AS `startDate`,
                           date_format(start, '%H:%i:%s') AS `startTime`,
                           date_format(end, '%Y-%m-%d')   AS `endDate`,
                           date_format(end, '%H:%i:%s')   AS `endTime`
                FROM appointments AS a
                         INNER JOIN customers AS c ON a.customer_id = c.customer_id
                         INNER JOIN users AS u ON a.user_id = u.user_id
                         INNER JOIN contacts AS co ON a.contact_id = co.contact_id
                                """;
    }

    public static void createAppointment(String title, String description, String location, String type, String startDate, String startTime, String endDate, String endTime, int customerID, int userID, int contactID) {
        String startDateUtc = Utility.utcForDatabase(startDate, startTime);
        String endDateUtc = Utility.utcForDatabase(endDate, endTime);

        try {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO appointments (Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) VALUES (?, ?, ?, ?, ?, ?, NOW(), ?, NOW(), ?, ?, ?, ?)");
            stmt.setString(1, title);
            stmt.setString(2, description);
            stmt.setString(3, location);
            stmt.setString(4, type);
            stmt.setString(5, startDateUtc);
            stmt.setString(6, endDateUtc);
            stmt.setString(7, Main.getCurrentUser().getUserName());
            stmt.setString(8, Main.getCurrentUser().getUserName());
            stmt.setInt(9, customerID);
            stmt.setInt(10, userID);
            stmt.setInt(11, contactID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error creating appointment: " + e.getMessage());
        }
    }

    public static void updateAppointment(int appointmentID, String title, String description, String location, String type, String startDate, String startTime, String endDate, String endTime, int customerID, int userID, int contactID) {
        String startDateUtc = Utility.utcForDatabase(startDate, startTime);
        String endDateUtc = Utility.utcForDatabase(endDate, endTime);

        try {
            PreparedStatement stmt = conn.prepareStatement("UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Last_Update = NOW(), Last_Updated_By = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?");
            stmt.setString(1, title);
            stmt.setString(2, description);
            stmt.setString(3, location);
            stmt.setString(4, type);
            stmt.setString(5, startDateUtc);
            stmt.setString(6, endDateUtc);
            stmt.setString(7, Main.getCurrentUser().getUserName());
            stmt.setInt(8, customerID);
            stmt.setInt(9, userID);
            stmt.setInt(10, contactID);
            stmt.setInt(11, appointmentID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error updating appointment: " + e.getMessage());
        }
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
