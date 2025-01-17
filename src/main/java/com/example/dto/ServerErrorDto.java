package com.example.dto;

public class ServerErrorDto {
    String description;

    public ServerErrorDto(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
