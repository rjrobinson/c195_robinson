package model;

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
     * Gets country id.
     *
     * @return the country id
     */
    public int getCountryId() {
        return countryId;
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
