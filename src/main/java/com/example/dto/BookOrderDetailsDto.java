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
}
