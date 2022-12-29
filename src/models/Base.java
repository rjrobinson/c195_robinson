package models;

import database.Database;

import java.sql.Connection;

/**
 * The type Base.
 */
public abstract class Base {

    /**
     * The constant db connection.
     */
    public static final Database db = new Database();

    /**
     * The constant conn.
     * The connection to the database for all models
     */
    public static final Connection conn = db.getConnection();

}
