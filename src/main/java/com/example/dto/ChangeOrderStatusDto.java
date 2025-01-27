package com.example.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ChangeOrderStatusDto {
    public int id;
    public String newStatus;

    @JsonCreator
    public ChangeOrderStatusDto(@JsonProperty("id") int id,
                         @JsonProperty("newStatus") String newStatus) {
        this.id = id;
        this.newStatus = newStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(String newStatus) {
        this.newStatus = newStatus;
    }
}
