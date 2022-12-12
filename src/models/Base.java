package models;

import database.Database;

import java.sql.Connection;

public abstract class Base {

    public static final Database db = new Database();

    public static final Connection conn = db.getConnection();
}
