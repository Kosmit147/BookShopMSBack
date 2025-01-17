package com.example.responses;

public enum ResponseType {
    Ok,
    Error;

    public static ResponseType fromResponseHeader(String header) {
        switch (header) {
            case "Ok" -> { return ResponseType.Ok; }
            default -> { return ResponseType.Error; }
        }
    }

    public static String toHeader(ResponseType type) {
        switch (type) {
            case Ok -> { return "Ok"; }
            default -> { return "Error"; }
        }
    }

    public String toHeader() {
        return ResponseType.toHeader(this);
    }
}
