package models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static models.Base.conn;

public class Customer {

    private int customerID;
    private String customerName;
    private String address;
    private String postalCode;
    private String phone;
    private String createdBy;
    private String createDate;
    private String lastUpdate;
    private String lastUpdatedBy;
    private String divisionName;
    private String countryName;

    public Customer(int customerID, String customerName, String address, String postalCode, String phone, String createdBy, String createDate, String lastUpdate, String lastUpdatedBy, String divisionName, String countryName) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.createdBy = createdBy;
        this.createDate = createDate;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.divisionName = divisionName;
        this.countryName = countryName;
    }

    public Customer(String customerName, String address, String postalCode, String phone, String createdBy, String lastUpdatedBy, String divisionName) {
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.createdBy = createdBy;
        this.createDate = createDate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.divisionName = divisionName;
    }

    public Customer(int customerID, String customerName, String address, String postalCode, String phone, String lastUpdatedBy, String divisionName) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.lastUpdatedBy = lastUpdatedBy;
        this.divisionName = divisionName;
    }

    static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();

    public static ObservableList<Customer> getAllCustomers() throws SQLException {
        allCustomers.clear();

        PreparedStatement stmt = conn.prepareStatement(
                "SELECT c.Customer_ID, c.Customer_Name, c.Address, c.Postal_Code, c.Phone, c.Created_By, c.Create_Date, c.Last_Update, c.Last_Updated_By, d.Division, co.country FROM customers AS c JOIN first_level_divisions AS d ON c.Division_ID = d.Division_ID JOIN countries AS co ON d.Country_ID = co.Country_ID"
        );

        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Customer customer = new Customer(
                    rs.getInt("Customer_ID"),
                    rs.getString("Customer_Name"),
                    rs.getString("Address"),
                    rs.getString("Postal_Code"),
                    rs.getString("Phone"),
                    rs.getString("Created_By"),
                    rs.getString("Create_Date"),
                    rs.getString("Last_Update"),
                    rs.getString("Last_Updated_By"),
                    rs.getString("Division"),
                    rs.getString("country")
            );
            allCustomers.add(customer);
        }
        return allCustomers;
    }

    public static void deleteCustomer(int customerID) {

        try {
            // First - Delete any appointment associated with this customer
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM appointments WHERE customer_id = ?");
            stmt.setInt(1, customerID);
            stmt.executeUpdate();

            // Second - Delete the customer
            stmt = conn.prepareStatement("DELETE FROM customers WHERE customer_id = ?");
            stmt.setInt(1, customerID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error deleting customer: " + e.getMessage());
        }
    }


    public String formattedAddress() {
        return address + ", " + divisionName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public String getDivisionName() {
        return divisionName;
    }

    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public static void addCustomer(Customer customer) {
        allCustomers.add(customer);
    }

    public String getCountryName() {
        return countryName;
    }

    public void create() {
        Division division = Division.getIdFromName(this.divisionName);

        try {
            PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Created_By, Create_Date, Last_Update, Last_Updated_By, Division_ID) VALUES (?, ?, ?, ?, ?, NOW(), NOW(), ?, ?)"
            );
            stmt.setString(1, this.customerName);
            stmt.setString(2, this.address);
            stmt.setString(3, this.postalCode);
            stmt.setString(4, this.phone);
            stmt.setString(5, this.createdBy);
            stmt.setString(6, this.lastUpdatedBy);
            stmt.setInt(7, division.getDivisionID());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error creating customer: " + e.getMessage());
        }
    }

    public void update() throws SQLException {
        Division division = Division.getIdFromName(this.divisionName);

        try {
            PreparedStatement stmt = conn.prepareStatement(
                    "UPDATE customers SET Customer_Name = ?,Address = ?, Postal_Code  = ?, Phone  = ?, Last_Update = now(), Last_Updated_By  = ?, Division_ID = ? WHERE Customer_ID = ?"
            );
            stmt.setString(1, this.customerName);
            stmt.setString(2, this.address);
            stmt.setString(3, this.postalCode);
            stmt.setString(4, this.phone);
            stmt.setString(5, this.lastUpdatedBy);
            stmt.setInt(6, division.getDivisionID());
            stmt.setInt(7, this.customerID);

            System.out.println(stmt);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error updating customer: " + e.getMessage());
        }
    }
}
