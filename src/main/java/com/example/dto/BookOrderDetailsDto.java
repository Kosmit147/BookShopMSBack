package com.example.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BookOrderDetailsDto {
    public int quantity;
    public BookDto book;

    @JsonCreator
    public BookOrderDetailsDto(@JsonProperty("quantity") int quantity,
                               @JsonProperty("book") BookDto book) {
        this.quantity = quantity;
        this.book = book;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BookDto getBook() {
        return book;
    }

    public void setBook(BookDto book) {
        this.book = book;
    }
}
