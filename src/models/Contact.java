package models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static models.Base.conn;

/**
 * The type Contact.
 */
public class Contact {
    private int contactID;
    private String contactName;
    private String email;

    /**
     * Instantiates a new Contact.
     *
     * @param contactID   the contact id
     * @param contactName the contact name
     * @param email       the email
     *                    The Contact model
     *                    This model is used to create Contact objects
     */
    public Contact(int contactID, String contactName, String email) {
        this.contactID = contactID;
        this.contactName = contactName;
        this.email = email;
    }

    /**
     * Gets contact id.
     *
     * @return the contact id
     */
    public int getContactID() {
        return contactID;
    }


    /**
     * Find contact. ( by contact name )
     *
     * @param contactName the contact name
     * @return the contact
     * @throws SQLException the sql exception
     */
    public static Contact find(String contactName) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT Contact_ID, Contact_Name, Email FROM contacts WHERE Contact_Name = ?");
        stmt.setString(1, contactName);
        ResultSet rs = stmt.executeQuery();

        return buildContact(rs);
    }

    /**
     * Find contact. ( by contact ID )
     *
     * @param contactID the contact id
     * @return the contact
     * @throws SQLException the sql exception
     */
    public static Contact find(int contactID) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT Contact_ID, Contact_Name, Email FROM contacts WHERE Contact_ID = ?");
        stmt.setInt(1, contactID);
        ResultSet rs = stmt.executeQuery();

        return buildContact(rs);
    }

    /**
     * Build contact contact.
     *
     * @param rs the ResultSet from the previous DB query
     * @return the contact
     * @throws SQLException the sql exception
     *                      This method is used to build a Contact object from the ResultSet
     */
    private static Contact buildContact(ResultSet rs) throws SQLException {
        Contact contact = null;
        if (rs.next()) {
            contact = new Contact(
                    rs.getInt("contact_Id"),
                    rs.getString("contact_Name"),
                    rs.getString("email")
            );
        }
        return contact;
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
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * The All contacts.
     */
    public static ObservableList<Contact> allContacts = FXCollections.observableArrayList();

    /**
     * Gets all contacts.
     *
     * @return the all contacts
     * @throws SQLException the sql exception
     *                      <p>
     *                      This method is used to get all contacts from the database
     */
    public static ObservableList<Contact> getAllContacts() throws SQLException {
        allContacts.clear();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM contacts");

        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Contact contact = new Contact(
                    rs.getInt("Contact_ID"),
                    rs.getString("Contact_Name"),
                    rs.getString("Email")
            );
            allContacts.add(contact);

        }
        return allContacts;
    }

    /**
     * Gets contact names.
     *
     * @return the contact names
     * @throws SQLException the sql exception
     *                      <p>
     *                      This method is used to get all contact names from the database
     *                      Also uses a Lambda expression to return the contact names
     */
    public static ObservableList<String> getContactNames() throws SQLException {
        ObservableList<String> contactNames = FXCollections.observableArrayList();

        getAllContacts().forEach(contact -> contactNames.add(contact.getContactName()));
        return contactNames;
    }
}
