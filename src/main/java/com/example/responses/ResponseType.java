package com.example.responses;

public enum ResponseType {
    Ok,
    ServerError;

    public static ResponseType fromResponseHeader(String header) {
        switch (header) {
            case "Ok" -> { return ResponseType.Ok; }
            default -> { return ResponseType.ServerError; }
        }
    }

    public static String toHeader(ResponseType type) {
        switch (type) {
            case Ok -> { return "Ok"; }
            default -> { return "ServerError"; }
        }
    }

    public String toHeader() {
        return ResponseType.toHeader(this);
    }
}
