package models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static models.Base.conn;

/**
 * The type Appointment report.
 */
public class AppointmentReport {

    /**
     * The Month.
     */
    public String month;
    /**
     * The Type.
     */
    public String type;
    /**
     * The Count.
     */
    public int count;

    /**
     * Instantiates a new Appointment report.
     *
     * @param month the month
     * @param type  the type
     * @param count the count
     */
    public AppointmentReport(String month, String type, int count) {
        this.month = month;
        this.type = type;
        this.count = count;
    }

    /**
     * Gets month.
     *
     * @return the month
     */
    public String getMonth() {
        return month;
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
     * Gets count.
     *
     * @return the count
     */
    public int getCount() {
        return count;
    }

    /**
     * Gets appointment report.
     *
     * @return the appointment report
     * @throws SQLException the sql exception
     *                      Gets the appointment report
     *                      Returns an observable list of appointment reports
     */
    public static ObservableList<AppointmentReport> getAppointmentReport() throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(
                """
                            SELECT MONTHNAME(start) as month, type, COUNT(*) as count
                                   FROM appointments
                                   GROUP BY type, month
                                   ORDER BY month ASC
                        """
        );

        ResultSet rs = stmt.executeQuery();
        ObservableList<AppointmentReport> appointmentReport = FXCollections.observableArrayList();
        while (rs.next()) {
            appointmentReport.add(new AppointmentReport(rs.getString("month"), rs.getString("type"), rs.getInt("count")));
        }
        return appointmentReport;
    }
}
