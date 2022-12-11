package models;

import database.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

    public int getUserId() {
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
        Database db = new Database();
        Connection conn = db.getConnection();

        PreparedStatement stmt = conn.prepareStatement(
                "SELECT user_id FROM users WHERE user_name=? AND password=? LIMIT 1"
        );

        stmt.setString(1, username);
        stmt.setString(2, password);

        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            new User(rs.getInt("user_id"), username);
            System.out.println("User logged in: " + username);
            return true;
        }

        return false;
    }
}
