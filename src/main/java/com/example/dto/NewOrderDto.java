package com.example.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class NewOrderDto {
    public String firstName;
    public String lastName;
    public String street;
    public String city;
    public String zip;
    public String date;
    public int userId;
    public BookOrderInfoDto[] books;

    @JsonCreator
    public NewOrderDto(@JsonProperty("firstName") String firstName,
                       @JsonProperty("lastName") String lastName,
                       @JsonProperty("street") String street,
                       @JsonProperty("city") String city,
                       @JsonProperty("zip") String zip,
                       @JsonProperty("date") String date,
                       @JsonProperty("userId") int userId,
                       @JsonProperty("books") BookOrderInfoDto[] books) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.street = street;
        this.city = city;
        this.zip = zip;
        this.date = date;
        this.userId = userId;
        this.books = books;
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public BookOrderInfoDto[] getBooks() {
        return books;
    }

    public void setBooks(BookOrderInfoDto[] books) {
        this.books = books;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
