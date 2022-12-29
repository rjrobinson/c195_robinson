package models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static models.Base.conn;

/**
 * The type Customer report.
 */
public class CustomerReport {
    /**
     * The Division.
     */
    public Division division;

    /**
     * The Count.
     */
    public int count;
    /**
     * The Percentage.
     */
    public String percentage;

    /**
     * Instantiates a new Customer report.
     *
     * @param division   the division
     * @param count      the count
     * @param percentage the percentage
     * @throws SQLException the sql exception
     */
    public CustomerReport(String division, int count, String percentage) throws SQLException {
        this.division = Division.find(division);
        this.count = count;
        this.percentage = percentage;
    }

    /**
     * Gets division Name.
     *
     * @return the division name
     */
    public String getDivision() {
        return division.division;
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
     * Gets percentage.
     *
     * @return the percentage
     */
    public String getPercentage() {
        return percentage;
    }
    /**
     * Gets customer report.
     *
     * @return the customer report
     * @throws SQLException the sql exception
     *                      This method is used to get all customers by division
     *                      and calculate the percentage of customers in each division
     *                      and return the results in an observable list
     *                      This is used the Report #3
     */
    public static ObservableList<CustomerReport> getCustomerReport() throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(
                """
                        SELECT count(*)                                            as count,
                               d.division,
                               (count(*) / (select count(*) from customers) * 100) as percentage
                        FROM customers
                                 INNER JOIN first_level_divisions d ON customers.division_id = d.division_id
                        GROUP BY d.division

                                                """
        );

        ResultSet rs = stmt.executeQuery();
        ObservableList<CustomerReport> customerReport = FXCollections.observableArrayList();
        while (rs.next()) {
            customerReport.add(new CustomerReport(
                    rs.getString("division"),
                    rs.getInt("count"),
                    rs.getString("percentage")
            ));
        }

        return customerReport;
    }
}
