package database;

import java.sql.SQLException;

public class DatabaseError extends Exception {

    public DatabaseError(String message) {
        super(message);
    }

    public DatabaseError(String message, SQLException e) {
        super(message, e);
    }
}
