package models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static models.Base.conn;

/**
 * The type Country.
 */
public class Country {
    private int countryId;
    private String country;
    private String createDate;
    private String createdBy;
    private String lastUpdate;
    private String lastUpdateBy;

    /**
     * Instantiates a new Country.
     *
     * @param countryId    the country id
     * @param country      the country
     * @param createDate   the create date
     * @param createdBy    the created by
     * @param lastUpdate   the last update
     * @param lastUpdateBy the last update by
     *                     The Country model
     *                     This model is used to create Country objects
     */
    public Country(int countryId, String country, String createDate, String createdBy, String lastUpdate, String lastUpdateBy) {
        this.countryId = countryId;
        this.country = country;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdateBy = lastUpdateBy;
    }


    /**
     * All countries.
     */
    static ObservableList<Country> allCountries = FXCollections.observableArrayList();

    /**
     * All country names.
     */
    static ObservableList<String> allCountryNames = FXCollections.observableArrayList();

    /**
     * Instantiates a new Country.
     *
     * @param country the country
     */
    public Country(String country) {
        this.country = country;
    }

    /**
     * Gets all countries.
     *
     * @return the all countries
     * @throws SQLException the sql exception
     *                     Gets all countries from the database
     *                     This method is used to populate the country combo box
     */
    public static ObservableList<String> getAllCountries() throws SQLException {
        allCountryNames.clear();

        PreparedStatement stmt = conn.prepareStatement(
                "SELECT country FROM countries"
        );

        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            allCountryNames.add(rs.getString("country"));
        }
        return allCountryNames;
    }

    /**
     * Gets country id.
     *
     * @return the country id
     */
    public String getCountryId() {
        return String.valueOf(countryId);
    }

    /**
     * Gets country.
     *
     * @return the country
     */
    public String getCountry() {
        return country;
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
}
