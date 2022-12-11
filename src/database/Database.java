package database;

import java.sql.*;

public class Database {

    // The database URL
    private static final String DB_URL = "jdbc:mysql://localhost:3306/";
    private static final String DB_NAME = "client_schedule";
    // The database user name and password
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    // The JDBC driver name and database URL
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";

    // The connection to the database
    private Connection conn;

    public Database() {
        try {
            // Open a connection to the database
            String url = DB_URL + DB_NAME;
            conn = DriverManager.getConnection(url, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Returns the connection to the database
    public Connection getConnection() {
        return conn;
    }

}
