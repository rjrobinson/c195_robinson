package models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.Main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static models.Base.conn;

public class User {
    private int userId;
    private String userName;
    private String password;
    private int active;
    private String createDate;
    private String createdBy;
    private String lastUpdate;
    private String lastUpdateBy;

    public User(int userId, String userName, String password, int active, String createDate, String createdBy, String lastUpdate, String lastUpdateBy) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.active = active;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdateBy = lastUpdateBy;
    }

    public User(int userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    public static int getUserID(String userName) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT user_id FROM users WHERE users.user_name = ?");
        stmt.setString(1, userName);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getInt("user_id");
        }
        return 0;
    }

    public int getUserID() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public int getActive() {
        return active;
    }

    public String getCreateDate() {
        return createDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    //    Database Operations
    public static boolean validateUser(String username, String password) throws SQLException {

        PreparedStatement stmt = conn.prepareStatement(
                "SELECT user_id FROM users WHERE user_name=? AND password=? LIMIT 1"
        );

        stmt.setString(1, username);
        stmt.setString(2, password);

        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            Main.setCurrentUser(new User(rs.getInt("user_id"), username));
            System.out.println("User logged in: " + username);
            return true;
        }

        return false;
    }


    private static ObservableList<User> allUsers = FXCollections.observableArrayList();

    public static ObservableList<User> getAllUsers() throws SQLException {
        allUsers.clear();

        PreparedStatement stmt = conn.prepareStatement(
                "SELECT * FROM users"
        );

        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            User user = new User(
                    rs.getInt("user_id"),
                    rs.getString("user_name")

            );

            allUsers.add(user);
        }
        return allUsers;
    }

    public static ObservableList<String> getUserNames() throws SQLException {
        ObservableList<String> userNames = FXCollections.observableArrayList();

        getAllUsers().forEach(user -> userNames.add(user.getUserName()));
        return userNames;
    }

}
