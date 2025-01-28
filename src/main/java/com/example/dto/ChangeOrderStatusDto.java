package com.example.dto;

import com.example.repositories.OrderStatus;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ChangeOrderStatusDto {
    public int id;
    public OrderStatus newStatus;

    @JsonCreator
    public ChangeOrderStatusDto(@JsonProperty("id") int id,
                                @JsonProperty("newStatus") OrderStatus newStatus) {
        this.id = id;
        this.newStatus = newStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public OrderStatus getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(OrderStatus newStatus) {
        this.newStatus = newStatus;
    }
}
