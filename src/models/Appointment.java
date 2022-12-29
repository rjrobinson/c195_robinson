package models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import main.Main;
import support.SceneHelper;
import support.Utility;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicReference;

import static models.Base.conn;

/**
 * The model Appointment.
 */
public class Appointment {


    public String period;

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

    private Customer customer;

    private Contact contact;


    /**
     * Instantiates a new Appointment.
     *
     * @param appointmentID the appointment id
     * @param title         the title
     * @param description   the description
     * @param location      the location
     * @param type          the type
     * @param start         the start
     * @param end           the end
     * @param createDate    the create date
     * @param createdBy     the created by
     * @param lastUpdate    the last update
     * @param lastUpdateBy  the last update by
     * @param contactName   the contact name
     * @param customerName  the customer name
     * @param userName      the user name
     * @param startDate     the start date
     * @param startTime     the start time
     * @param endDate       the end date
     * @param endTime       the end time
     * @throws SQLException the sql exception
     *                      <p>
     *                      This constructor is used to create an appointment object from the database
     */
//constructor
    public Appointment(int appointmentID, String title, String description, String location, String type, String start, String end, String createDate, String createdBy, String lastUpdate, String lastUpdateBy, String contactName, String customerName, String userName, String startDate, String startTime, String endDate, String endTime) throws SQLException {
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
        this.contact = Contact.find(contactName);
        this.customer = Customer.find(customerName);
        this.userName = userName;
        this.startDate = startDate;
        this.startTime = startTime;
        this.endDate = endDate;
        this.endTime = endTime;
    }

    /**
     * Instantiates a new Appointment.
     *
     * @param appointmentID the appointment id
     * @param title         the title
     * @param description   the description
     * @param location      the location
     * @param type          the type
     * @param start         the start
     * @param end           the end
     * @param createDate    the create date
     * @param createdBy     the created by
     * @param lastUpdate    the last update
     * @param lastUpdateBy  the last update by
     * @param contactName   the contact name
     * @param customerName  the customer name
     * @param userName      the user name
     * @param startDate     the start date
     * @param startTime     the start time
     * @param endDate       the end date
     * @param endTime       the end time
     * @param period        the period
     * @throws SQLException the sql exception
     */
    public Appointment(int appointmentID, String title, String description, String location, String type, String start, String end, String createDate, String createdBy, String lastUpdate, String lastUpdateBy, String contactName, String customerName, String userName, String startDate, String startTime, String endDate, String endTime, String period) throws SQLException {
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
        this.customer = Customer.find(customerName);
        this.userName = userName;
        this.startDate = startDate;
        this.startTime = startTime;
        this.endDate = endDate;
        this.endTime = endTime;
        this.period = period;
    }

    /**
     * The All appointments.
     */
    static ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
    /**
     * The By week appointments.
     */
    static ObservableList<Appointment> byWeekAppointments = FXCollections.observableArrayList();
    /**
     * The By month appointments.
     */
    static ObservableList<Appointment> byMonthAppointments = FXCollections.observableArrayList();

    /**
     * The Appointment by contact.
     */
    static ObservableList<Appointment> apptByContact = FXCollections.observableArrayList();

    /**
     * Gets all appointments.
     *
     * @return the all appointments
     * @throws SQLException the sql exception
     */
    public static ObservableList<Appointment> getAllAppointments() throws SQLException {
        allAppointments.clear();
        PreparedStatement stmt = conn.prepareStatement(allApptSql());

        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            String startTime = rs.getString("start");
            String endTime = rs.getString("end");

            String startTimeLocal = Utility.toLocal(startTime);
            String endTimeLocal = Utility.toLocal(endTime);

            Appointment appt = new Appointment(
                    rs.getInt("Appointment_ID"),
                    rs.getString("Title"),
                    rs.getString("Description"),
                    rs.getString("Location"),
                    rs.getString("Type"),
                    startTimeLocal,
                    endTimeLocal,
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

    /**
     * Gets appointments by week.
     *
     * @return the appointments by week
     * @throws SQLException the sql exception
     */
    public static ObservableList<Appointment> getAppointmentsByWeek() throws SQLException {
        byWeekAppointments.clear();
        String byWeekSQL = """
                                SELECT DATE_FORMAT(DATE_ADD(start, INTERVAL 1 - DAYOFWEEK(start) DAY), '%Y-%m-%d') as period_start,
                                       a.appointment_id,
                                       c.customer_name,
                                       u.user_name,
                                       co.contact_name,
                                       a.title,
                                       a.description,
                                       a.type,
                                       a.location,
                                       date_format(a.start, '%Y-%m-%dT%H:%i:%sZ')                                  as start,
                                       date_format(a.end, '%Y-%m-%dT%H:%i:%sZ')                                    as end,
                                       a.create_date,
                                       a.created_by,
                                       a.last_update,
                                       a.last_updated_by,
                                       date_format(start, '%Y-%m-%d')                                              AS `startDate`,
                                       date_format(start, '%H:%i:%s')                                              AS `startTime`,
                                       date_format(end, '%Y-%m-%d')                                                AS `endDate`,
                                       date_format(end, '%H:%i:%s')                                                AS `endTime`
                                FROM appointments AS a
                                         INNER JOIN customers AS c ON a.customer_id = c.customer_id
                                         INNER JOIN users AS u ON a.user_id = u.user_id
                                         INNER JOIN contacts AS co ON a.contact_id = co.contact_id
                                GROUP BY period_start, Appointment_ID, customer_name, user_name, contact_name, title, description, type, location,
                                         start, end, create_date, created_by, last_update, last_updated_by, startDate, startTime, endDate, endTime;
                """;

        PreparedStatement stmt = conn.prepareStatement(byWeekSQL);

        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            String startTime = rs.getString("start");
            String endTime = rs.getString("end");

            String startTimeLocal = Utility.toLocal(startTime);
            String endTimeLocal = Utility.toLocal(endTime);

            Appointment appt = new Appointment(
                    rs.getInt("Appointment_ID"),
                    rs.getString("Title"),
                    rs.getString("Description"),
                    rs.getString("Location"),
                    rs.getString("Type"),
                    startTimeLocal,
                    endTimeLocal,
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
                    endTimeLocal.substring(11, 16),
                    rs.getString("period_start")
            );
            byWeekAppointments.add(appt);
        }
        return byWeekAppointments;
    }

    /**
     * Gets appointments by month.
     *
     * @return the appointments by month
     * @throws SQLException the sql exception
     */
    public static ObservableList<Appointment> getAppointmentsByMonth() throws SQLException {
        byMonthAppointments.clear();
        String byMonthSQL = """
                SELECT DATE_FORMAT(DATE_ADD(a.start, INTERVAL 1 - DAYOFWEEK(start) DAY), '%M') as period_start,
                       a.appointment_id,
                       c.customer_name,
                       u.user_name,
                       co.contact_name,
                       a.title,
                       a.description,
                       a.type,
                       a.location,
                       date_format(a.start, '%Y-%m-%dT%H:%i:%sZ')                                  as start,
                       date_format(a.end, '%Y-%m-%dT%H:%i:%sZ')                                    as end,
                       a.create_date,
                       a.created_by,
                       a.last_update,
                       a.last_updated_by,
                       date_format(start, '%Y-%m-%d')                                              AS `startDate`,
                       date_format(start, '%H:%i:%s')                                              AS `startTime`,
                       date_format(end, '%Y-%m-%d')                                                AS `endDate`,
                       date_format(end, '%H:%i:%s')                                                AS `endTime`
                FROM appointments AS a
                         INNER JOIN customers AS c ON a.customer_id = c.customer_id
                         INNER JOIN users AS u ON a.user_id = u.user_id
                         INNER JOIN contacts AS co ON a.contact_id = co.contact_id
                GROUP BY period_start, Appointment_ID, customer_name, user_name, contact_name, title, description, type, location,
                         start, end, create_date, created_by, last_update, last_updated_by, startDate, startTime, endDate, endTime
                ORDER BY (MONTH(a.start) = MONTH(CURDATE())) DESC, MONTH(a.start) ASC, WEEK(a.start) ASC
                                """;

        PreparedStatement stmt = conn.prepareStatement(byMonthSQL);

        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            String startTime = rs.getString("start");
            String endTime = rs.getString("end");

            String startTimeLocal = Utility.toLocal(startTime);
            String endTimeLocal = Utility.toLocal(endTime);

            Appointment appt = new Appointment(
                    rs.getInt("Appointment_ID"),
                    rs.getString("Title"),
                    rs.getString("Description"),
                    rs.getString("Location"),
                    rs.getString("Type"),
                    startTimeLocal,
                    endTimeLocal,
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
                    endTimeLocal.substring(11, 16),
                    rs.getString("period_start")
            );
            byMonthAppointments.add(appt);
        }
        return byMonthAppointments;
    }

    /**
     * Gets Appointment by contact.
     *
     * @param contactID the contact id
     * @return the Appointment by contact
     * @throws SQLException the sql exception
     *                      <p>
     *                      This method is used to get all appointments by contact.
     *                      <p>
     *                      It is used in the Contact Schedule Report.
     *                      This uses a Lambda to filter the appointments by contact.
     */
    public static ObservableList<Appointment> getApptByContact(int contactID) throws SQLException {
        apptByContact.clear();
        allAppointments.stream().filter(appt -> appt.contact.getContactID() == contactID).forEach(appt -> {
            apptByContact.add(appt);
        });
        return apptByContact;
    }

    /**
     * Delete appointment.
     *
     * @param appointmentID the appointment id
     *                      This method is used to delete an appointment.
     */
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

    /**
     * Where I started creating the SQL for the AllAppointments Report.
     *
     * @return the string
     */
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

    /**
     * Create appointment.
     *
     * @param title       the title
     * @param description the description
     * @param location    the location
     * @param type        the type
     * @param startDate   the start date
     * @param startTime   the start time
     * @param endDate     the end date
     * @param endTime     the end time
     * @param customerID  the customer id
     * @param userID      the user id
     * @param contactID   the contact id
     *                    <p>
     *                    This method is used to create an appointment.
     */
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

    /**
     * Update appointment.
     *
     * @param appointmentID the appointment id
     * @param title         the title
     * @param description   the description
     * @param location      the location
     * @param type          the type
     * @param startDate     the start date
     * @param startTime     the start time
     * @param endDate       the end date
     * @param endTime       the end time
     * @param customerID    the customer id
     * @param userID        the user id
     * @param contactID     the contact id
     *
     *                      This method is used to update an existing appointment.
     */
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

    /**
     * Checks and alerts user if they have an upcoming appointments within 15 minutes.
     *
     * @param userID the user id
     * @throws IOException  the io exception
     * @throws SQLException the sql exception
     */
    public static void checkForSoonAppointments(int userID) throws IOException, SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM appointments WHERE User_ID = ? AND Start BETWEEN UTC_TIMESTAMP() AND DATE_ADD(UTC_TIMESTAMP(), INTERVAL 15 MINUTE);");
        stmt.setInt(1, userID);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            SceneHelper.displayAlert(Alert.AlertType.INFORMATION, "Appointment Alert, You have an appointment coming up soon!");
        }
    }

    /**
     * Gets appointment id.
     *
     * @return the appointment id
     */
    public int getAppointmentID() {
        return appointmentID;
    }

    /**
     * Gets period.
     *
     * @return the period
     */
    public String getPeriod() {
        return period;
    }

    /**
     * Gets title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets location.
     *
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Gets start.
     *
     * @return the start
     */
    public String getStart() {
        return start;
    }

    /**
     * Gets end.
     *
     * @return the end
     */
    public String getEnd() {
        return end;
    }

    /**
     * Sets last update.
     *
     * @param lastUpdate the last update
     */
    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * Gets last update by.
     *
     * @return the last update by
     */
    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    /**
     * Gets start date.
     *
     * @return the start date
     */
    public String getStartDate() {
        return start.substring(0, 10);
    }

    /**
     * Gets contact name.
     *
     * @return the contact name
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * Gets customer name.
     *
     * @return the customer name
     */
    public String getCustomerName() {
        return customer.getCustomerName();
    }

    /**
     * Gets user name.
     *
     * @return the user name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Gets start time.
     *
     * @return the start time
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * Gets end time.
     *
     * @return the end time
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     * Gets end date.
     *
     * @return the end date
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * Appointment overlaps boolean.
     *
     * @param start      the start
     * @param end        the end
     * @param excludeID  the exclude id
     * @param customerID the customer id
     * @return the boolean
     * @throws IOException  the io exception
     * @throws SQLException the sql exception
     *                     This method is used to check if an appointment overlaps with another appointment.
     *                     It is used when creating a new appointment and updating an existing appointment.
     *
     *                     A Lambda is also uses here to loop though the list of appointments and check if the appointment overlaps.
     */
    public static Boolean appointmentOverlaps(LocalDateTime start, LocalDateTime end, String excludeID, int customerID) throws IOException, SQLException {
        AtomicReference<Boolean> hasOverlaps = new AtomicReference<>(false);

        allAppointments = getAllAppointments();
        allAppointments.forEach(appointment -> {
            if ((excludeID != null && appointment.getAppointmentID() == Integer.parseInt(excludeID) || customerID != appointment.customer.getCustomerID())) {
                System.out.println("Skipping appointment " + appointment.getAppointmentID());
            } else {
                LocalDateTime appointmentStart = Utility.buildFromString(appointment.getStart().replace("T", " "));
                LocalDateTime appointmentEnd = Utility.buildFromString(appointment.getEnd().replace("T", " "));

                if (Utility.isBetween(appointmentStart, appointmentEnd, start) || Utility.isBetween(appointmentStart, appointmentEnd, end)) {
                    hasOverlaps.set(true);
                }
            }
        });
        return hasOverlaps.get();
    }
}
