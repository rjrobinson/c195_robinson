package models;

import database.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class Base {

    public static final Database db = new Database();

    public static final Connection conn = db.getConnection();

    public String tableName;
    public String modelName;

    public Base(String tableName, String modelName) {
        this.modelName = modelName;
        this.tableName = tableName;
    }
//
//    // Method to retrieve records from the database based on a given set of criteria
//    public static ResultSet find(String criteria) throws SQLException {
//        // Build the SQL query using the criteria and table name
//        String sql = "SELECT * FROM " + tableName + " WHERE " + criteria;
//
//        // Execute the query and return the result set
//        return conn.createStatement().executeQuery(sql);
//    }
//
    public ResultSet find(int id) throws SQLException {
        // Build the SQL query using the criteria and table name
        String tableIdColumn = modelName + "_id";

        PreparedStatement stmt = conn.prepareStatement(
                "SELECT * FROM ? WHERE ?=? LIMIT 1"
        );

        stmt.setString(1, tableName);
        stmt.setString(2, tableIdColumn);
        stmt.setInt(3, id);
        // Execute the query and return the result set
        return stmt.executeQuery();
    }
}
