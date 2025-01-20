package com.example.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BookOrderInfo {
    public int quantity;
    public int id;

    @JsonCreator
    public BookOrderInfo(@JsonProperty("quantity") int quantity,
                      @JsonProperty("id") int id) {
        this.quantity = quantity;
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
