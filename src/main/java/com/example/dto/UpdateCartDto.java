package com.example.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UpdateCartDto {
    public String userEmail;
    public String[] bookTitles;

    @JsonCreator
    public UpdateCartDto(@JsonProperty("userEmail") String userEmail,
                         @JsonProperty("bookTitles") String[] bookTitles) {
        this.userEmail = userEmail;
        this.bookTitles = bookTitles;
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
}
