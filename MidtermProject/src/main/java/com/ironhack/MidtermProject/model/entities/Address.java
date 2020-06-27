package com.ironhack.MidtermProject.model.entities;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotEmpty;

/**
 * Address class is used to define the address from AccountHolder's entity.
 */
@Embeddable
public class Address {
    /**
     * Address country.
     */
    @NotEmpty(message = "Please introduce your country.")
    private String country;

    /**
     * Address city.
     */
    @NotEmpty(message = "Please introduce your city.")
    private String city;

    /**
     * Address street.
     */
    @NotEmpty(message = "Please introduce your street.")
    private String street;

    /**
     * Address postalCode.
     */
    @NotEmpty(message = "Please introduce your postal code.")
    private String postalCode;


    /**
     * Address Void Constructor.
     */
    public Address() {}


    /**
     * Address constructor.
     * @param country Receives a string of country for the Address.
     * @param city Receives a string of city for the Address.
     * @param street Receives a string of street for the Address.
     * @param postalCode Receives a string of postalCode for the Address.
     */
    public Address(String country, String city, String street, String postalCode) {
        this.country = country;
        this.city = city;
        this.street = street;
        this.postalCode = postalCode;
    }

    /**
     * Getter of Address' country.
     * @return a string with country.
     */
    public String getCountry() {
        return country;
    }

    /**
     * Setter of Address' country.
     * @param country receives a string of country.
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Getter of Address' city.
     * @return a string with city.
     */
    public String getCity() {
        return city;
    }

    /**
     * Setter of Address' city.
     * @param city receives a string of city.
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Getter of Address' street.
     * @return a string with street.
     */
    public String getStreet() {
        return street;
    }

    /**
     * Setter of Address' street.
     * @param street receives a string of street.
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     * Getter of Address's postalCode.
     * @return a string with postalCode.
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Setter of Address's postalCode.
     * @param postalCode receives a string of postalCode.
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * Representation of Address in form of String.
     * @return a string of Address.
     */
    @Override
    public String toString() {
        return "Address{" +
                "country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", postalCode='" + postalCode + '\'' +
                '}';
    }
}
