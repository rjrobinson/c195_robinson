package models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static models.Base.conn;
public class AppointmentReport {

    public String month;
    public String type;
    public int count;

    public AppointmentReport(String month, String type, int count) {
        this.month = month;
        this.type = type;
        this.count = count;
    }

    public String getMonth() {
        return month;
    }

    public String getType() {
        return type;
    }

    public int getCount() {
        return count;
    }

    public static ObservableList<AppointmentReport> getAppointmentReport() throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(
                """
                        SELECT MONTHNAME(start) as month, type, COUNT(*) as total
                               FROM appointments
                               GROUP BY type, month
                               ORDER BY month ASC

                                                       """
        );

        ResultSet rs = stmt.executeQuery();
        ObservableList<AppointmentReport> appointmentReport = FXCollections.observableArrayList();
        while (rs.next()) {
            appointmentReport.add(new AppointmentReport(rs.getString("month"), rs.getString("type"), rs.getInt("total")));
        }
        return appointmentReport;
    }
}
