package models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static models.Base.conn;

public class Division {

    @FXML
    private int divisionId;
    @FXML
    private String division;

public Integer getDivisionID() {
        return divisionId;
    }


    public Division(int divisionId, String division) {
        this.divisionId = divisionId;
        this.division = division;
    }

    public static ObservableList<String> divisionNames = FXCollections.observableArrayList();

    @FXML
    public static ObservableList<String> getDivisionsByCountry(String country) throws SQLException {
        divisionNames.clear();

        PreparedStatement stmt = conn.prepareStatement(
                "SELECT division.division FROM first_level_divisions as division INNER JOIN countries as c ON division.Country_ID = c.Country_ID WHERE c.country = ?"
        );
        stmt.setString(1, country);

        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            divisionNames.add(rs.getString("division"));

        }
        return divisionNames;
    }

    public static Division getIdFromName(String divisionName) {
        Division division = null;
        try {
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT division_id FROM first_level_divisions WHERE division = ?"
            );

            stmt.setString(1, divisionName);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                division = new Division(
                        rs.getInt("division_id"),
                        divisionName
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return division;
    }
}
