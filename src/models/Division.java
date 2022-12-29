package models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static models.Base.conn;

/**
 * The type Division.
 */
public class Division {

    @FXML
    private int divisionId;
    @FXML
    String division;

    /**
     * Gets division id.
     *
     * @return the division id
     */
    public Integer getDivisionID() {
        return divisionId;
    }


    /**
     * Instantiates a new Division.
     *
     * @param divisionId the division id
     * @param division   the division
     */
    public Division(int divisionId, String division) {
        this.divisionId = divisionId;
        this.division = division;
    }

    /**
     * The Division names.
     */
    public static ObservableList<String> divisionNames = FXCollections.observableArrayList();

    /**
     * Gets divisions by country.
     *
     * @param country the country
     * @return the divisions by country
     * @throws SQLException the sql exception                      This method is used to get all divisions by country
     */
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

    /**
     * Find division.
     *
     * @param divisionName the division name
     * @return the division
     * @throws SQLException the sql exception
     */
    public static Division find(String divisionName) throws SQLException {
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

    /**
     * Find division.
     *
     * @param divisionID the division id
     * @return the division
     * @throws SQLException the sql exception
     */
    public static Division find(int divisionID) throws SQLException {
        Division division = null;
        try {
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT division FROM first_level_divisions WHERE division_id = ?"
            );
            stmt.setInt(1, divisionID);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                division = new Division(
                        divisionID,
                        rs.getString("division")

                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return division;
    }
}
