package com.example.responses;

public enum ResponseType {
    Ok,
    NotFound,
    Error;

    public static ResponseType fromResponseHeader(String header) {
        switch (header) {
            case "Ok" -> { return ResponseType.Ok; }
            case "NotFound" -> { return ResponseType.NotFound; }
            default -> { return ResponseType.Error; }
        }
    }

    public static String toResponseHeader(ResponseType type) {
        switch (type) {
            case Ok -> { return "Ok"; }
            case NotFound -> { return "NotFound"; }
            default -> { return "Error"; }
        }
    }

    public String toResponseHeader() {
        return ResponseType.toResponseHeader(this);
    }
}
