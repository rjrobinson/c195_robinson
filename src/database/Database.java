package database;

import java.sql.*;

/**
 * The type Database.
 */
public class Database {

    // The database URL
//    private static final String DB_URL = "jdbc:mysql://localhost:3306/";
    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/";
    private static final String DB_NAME = "client_schedule";
    // The database user name and password
    private static final String USERNAME = "sqlUser";
    private static final String PASSWORD = "Passw0rd!";
    // The connection to the database
    private Connection conn;

    /**
     * Instantiates a new Connection to the Database.
     */
    public Database() {
        try {
            // Open a connection to the database
            String url = DB_URL + DB_NAME + "?connectionTimeZone=SERVER";

            conn = DriverManager.getConnection(url, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets connection.
     *
     * @return the connection
     * Returns the connection to the database
     */
    public Connection getConnection() {
        return conn;
    }

}
