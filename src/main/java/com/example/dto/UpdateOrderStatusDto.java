package com.example.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UpdateOrderStatusDto {
    public int id;
    public OrderStatus newStatus;

    @JsonCreator
    public UpdateOrderStatusDto(@JsonProperty("id") int id,
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
