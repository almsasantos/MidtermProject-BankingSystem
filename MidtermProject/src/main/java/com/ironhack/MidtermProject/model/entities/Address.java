package com.ironhack.MidtermProject.model.entities;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotEmpty;

@Embeddable
public class Address {
    @NotEmpty(message = "Please introduce your country.")
    private String country;
    @NotEmpty(message = "Please introduce your city.")
    private String city;
    @NotEmpty(message = "Please introduce your street.")
    private String street;
    @NotEmpty(message = "Please introduce your postal code.")
    private String postalCode;

    public Address() {}

    public Address(String country, String city, String street, String postalCode) {
        this.country = country;
        this.city = city;
        this.street = street;
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
}
