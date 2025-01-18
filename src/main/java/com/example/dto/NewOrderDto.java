package com.example.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class NewOrderDto {
    public String street;
    public String city;
    public String zip;
    public String date;
    public String userEmail;
    public String[] bookTitles;

    @JsonCreator
    public NewOrderDto(@JsonProperty("street") String street,
                       @JsonProperty("city") String city,
                       @JsonProperty("zip") String zip,
                       @JsonProperty("date") String date,
                       @JsonProperty("userEmail") String userEmail,
                       @JsonProperty("bookTitles") String[] bookTitles) {
        this.street = street;
        this.city = city;
        this.zip = zip;
        this.date = date;
        this.userEmail = userEmail;
        this.bookTitles = bookTitles;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String[] getBookTitles() {
        return bookTitles;
    }

    public void setBookTitles(String[] bookTitles) {
        this.bookTitles = bookTitles;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
