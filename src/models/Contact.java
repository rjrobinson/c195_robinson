package models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static models.Base.conn;

public class Contact {
    private int contactID;
    private String contactName;
    private String email;

    public Contact(int contactID, String contactName, String email) {
        this.contactID = contactID;
        this.contactName = contactName;
        this.email = email;
    }

    public int getContactID() {
        return contactID;
    }

    public static int getContactID(String contactName) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT Contact_ID FROM contacts WHERE Contact_Name = ?");
        stmt.setString(1, contactName);
        ResultSet rs = stmt.executeQuery();

        return rs.getInt("Contact_ID");
    }
    public String getContactName() {
        return contactName;
    }

    public String getEmail() {
        return email;
    }

    public static ObservableList<Contact> allContacts = FXCollections.observableArrayList();

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

    public static ObservableList<String> getContactNames() throws SQLException {
        ObservableList<String> contactNames = FXCollections.observableArrayList();

        getAllContacts().forEach(contact -> contactNames.add(contact.getContactName()));
        return contactNames;
    }
}
