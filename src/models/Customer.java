package models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static models.Base.conn;

/**
 * The type Customer.
 */
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

    private Division division;

    private Country country;

    private String countryName;

    /**
     * Instantiates a new Customer.
     *
     * @param customerID    the customer id
     * @param customerName  the customer name
     * @param address       the address
     * @param postalCode    the postal code
     * @param phone         the phone
     * @param createdBy     the created by
     * @param createDate    the create date
     * @param lastUpdate    the last update
     * @param lastUpdatedBy the last updated by
     * @param divisionName  the division name
     * @param countryName   the country name
     */
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

    /**
     * Instantiates a new Customer.
     *
     * @param customerName  the customer name
     * @param address       the address
     * @param postalCode    the postal code
     * @param phone         the phone
     * @param createdBy     the created by
     * @param lastUpdatedBy the last updated by
     * @param divisionName  the division name
     */
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

    /**
     * Instantiates a new Customer.
     *
     * @param customerID    the customer id
     * @param customerName  the customer name
     * @param address       the address
     * @param postalCode    the postal code
     * @param phone         the phone
     * @param lastUpdatedBy the last updated by
     * @param divisionName  the division name
     */
    public Customer(int customerID, String customerName, String address, String postalCode, String phone, String lastUpdatedBy, String divisionName) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.lastUpdatedBy = lastUpdatedBy;
        this.divisionName = divisionName;
    }

    /**
     * The All customers.
     */
    static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();

    /**
     * Gets all customers.
     *
     * @return the all customers
     * @throws SQLException the sql exception
     */
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

    /**
     * Gets customer names.
     *
     * @return the customer names
     * @throws SQLException the sql exception
     */
    public static ObservableList<String> getCustomerNames() throws SQLException {
        ObservableList<String> customerNames = FXCollections.observableArrayList();

        getAllCustomers().forEach(customer -> customerNames.add(customer.getCustomerName()));
        return customerNames;
    }


    /**
     * Delete customer.
     *
     * @param customerID the customer id
     */
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

    /**
     * Sets customer name.
     *
     * @param customerName the customer name
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * Gets customer id.
     *
     * @return the customer id
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * Find customer.
     *
     * @param customerName the customer name
     * @return the customer
     * @throws SQLException the sql exception
     * the find method is used to find a customer by customer_name and return the customer object
     */
    public static Customer find(String customerName) throws SQLException {
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT c.Customer_ID, c.Customer_Name, c.Address, c.Postal_Code, c.Phone, c.Created_By, c.Create_Date, c.Last_Update, c.Last_Updated_By, d.Division, co.country FROM customers AS c JOIN first_level_divisions AS d ON c.Division_ID = d.Division_ID JOIN countries AS co ON d.Country_ID = co.Country_ID WHERE customer_name = ?");
            stmt.setString(1, customerName);
            Customer customer;

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                customer = new Customer(
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
                return customer;
            }

        } catch (SQLException e) {
            System.out.println("Error getting customer ID: " + e.getMessage());
        }
        return null;
    }

    /**
     * Find customer.
     *
     * @param customerID the customer id
     * @return the customer
     * @throws SQLException the sql exception
     *
     *  the find method is used to find a customer by ID and return the customer object
     */
    public static Customer find(int customerID) throws SQLException {
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT c.Customer_ID, c.Customer_Name, c.Address, c.Postal_Code, c.Phone, c.Created_By, c.Create_Date, c.Last_Update, c.Last_Updated_By, d.Division, co.country FROM customers AS c JOIN first_level_divisions AS d ON c.Division_ID = d.Division_ID JOIN countries AS co ON d.Country_ID = co.Country_ID WHERE customer_ID = ?");
            stmt.setInt(1, customerID);
            Customer customer;

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                customer = new Customer(
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
                return customer;
            }

        } catch (SQLException e) {
            System.out.println("Error getting customer ID: " + e.getMessage());
        }
        return null;
    }

    /**
     * Sets customer id.
     *
     * @param customerID the customer id
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**
     * Gets address.
     *
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets address.
     *
     * @param address the address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets postal code.
     *
     * @return the postal code
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Sets postal code.
     *
     * @param postalCode the postal code
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * Gets phone.
     *
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets phone.
     *
     * @param phone the phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Gets created by.
     *
     * @return the created by
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Sets created by.
     *
     * @param createdBy the created by
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Gets create date.
     *
     * @return the create date
     */
    public String getCreateDate() {
        return createDate;
    }

    /**
     * Sets create date.
     *
     * @param createDate the create date
     */
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    /**
     * Gets last update.
     *
     * @return the last update
     */
    public String getLastUpdate() {
        return lastUpdate;
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
     * Gets last updated by.
     *
     * @return the last updated by
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * Sets last updated by.
     *
     * @param lastUpdatedBy the last updated by
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * Gets division name.
     *
     * @return the division name
     */
    public String getDivisionName() {
        return divisionName;
    }

    /**
     * Sets division name.
     *
     * @param divisionName the division name
     */
    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    /**
     * Gets customer name.
     *
     * @return the customer name
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Add customer.
     *
     * @param customer the customer
     */
    public static void addCustomer(Customer customer) {
        allCustomers.add(customer);
    }

    /**
     * Gets country name.
     *
     * @return the country name
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * Create customer in the database.
     *
     */
    public void create() throws SQLException {
        Division division = Division.find(this.divisionName);

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

    /**
     * Update.
     *
     * @throws SQLException the sql exception
     * Updates customer in the database.
     */
    public void update() throws SQLException {
        Division division = Division.find(this.divisionName);

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
