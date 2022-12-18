package models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static models.Base.conn;

public class Customer {

    public int customerID;
    public String customerName;
    public String address;
    public String postalCode;
    public String phone;
    public String createdBy;
    public String createDate;
    public String lastUpdate;
    public String lastUpdatedBy;
    public int divisionID;

    public Customer(int customerID, String customerName, String address, String postalCode, String phone, String createdBy, String createDate, String lastUpdate, String lastUpdatedBy, int divisionID) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.createdBy = createdBy;
        this.createDate = createDate;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.divisionID = divisionID;
    }

    static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();

    public static ObservableList<Customer> getAllCustomers() throws SQLException {

        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM customers");

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
                    rs.getInt("Division_ID")
            );
            allCustomers.add(customer);
        }
        return allCustomers;
    }

    public String getCustomerName() {
        return customerName;
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

    public int getDivisionID() {
        return divisionID;
    }

    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

}
