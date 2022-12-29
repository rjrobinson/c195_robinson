package models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.Main;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import static models.Base.conn;

/**
 * The type User.
 */
public class User {
    private int userId;
    private String userName;
    private String password;
    private int active;
    private String createDate;
    private String createdBy;
    private String lastUpdate;
    private String lastUpdateBy;

    /**
     * Instantiates a new User.
     *
     * @param userId       the user id
     * @param userName     the user name
     * @param password     the password
     * @param active       the active
     * @param createDate   the create date
     * @param createdBy    the created by
     * @param lastUpdate   the last update
     * @param lastUpdateBy the last update by
     */
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

    /**
     * Instantiates a new User.
     *
     * @param userId   the user id
     * @param userName the user name
     */
    public User(int userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    /**
     * Find user.
     *
     * @param userName the user name
     * @return the user
     * @throws SQLException the sql exception
     */
    public static User find(String userName) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT user_id, User_Name FROM users WHERE users.user_name = ?");
        stmt.setString(1, userName);
        ResultSet rs = stmt.executeQuery();

        return buildUser(rs);
    }

    /**
     * Find user.
     *
     * @param userID the user id
     * @return the user
     * @throws SQLException the sql exception
     */
    public static User find(int userID) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT user_id, User_Name FROM users WHERE users.user_id = ?");
        stmt.setInt(1, userID);
        ResultSet rs = stmt.executeQuery();

        return buildUser(rs);
    }

    private static User buildUser(ResultSet rs) throws SQLException {
        User user = null;
        if (rs.next()) {
            user = new User(
                    rs.getInt("user_Id"),
                    rs.getString("User_Name")
            );
        }
        return user;
    }

    /**
     * Gets user id.
     *
     * @return the user id
     */
    public int getUserID() {
        return userId;
    }

    /**
     * Gets user name.
     *
     * @return the user name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Gets password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Gets active.
     *
     * @return the active
     */
    public int getActive() {
        return active;
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
     * Gets created by.
     *
     * @return the created by
     */
    public String getCreatedBy() {
        return createdBy;
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
     * Gets last update by.
     *
     * @return the last update by
     */
    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    /**
     * Validate user boolean.
     *
     * @param username the username
     * @param password the password
     * @return the boolean
     * @throws SQLException the sql exception
     */
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

            recordLoginAttempt(username, true);
            return true;
        }
        recordLoginAttempt(username, false);
        return false;
    }


    private static ObservableList<User> allUsers = FXCollections.observableArrayList();

    /**
     * Gets all users.
     *
     * @return the all users
     * @throws SQLException the sql exception
     */
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

    /**
     * Gets user names.
     *
     * @return the user names
     * @throws SQLException the sql exception
     */
    public static ObservableList<String> getUserNames() throws SQLException {
        ObservableList<String> userNames = FXCollections.observableArrayList();

        getAllUsers().forEach(user -> userNames.add(user.getUserName()));
        return userNames;
    }

    // File to store login activity
    private static final String LOGIN_ACTIVITY_FILE = "login_activity.txt";

    /**
     * Record login attempt.
     *
     * @param username the username
     * @param success  the success
     */
    public static void recordLoginAttempt(String username, boolean success) {
        LocalDateTime now = LocalDateTime.now();

        String loginRecord = String.format("%s,%s,%s,%s", now.toLocalDate(),now.toLocalTime(), username, success);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(LOGIN_ACTIVITY_FILE, true))) {
            bw.write(loginRecord);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
