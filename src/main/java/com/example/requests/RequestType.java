package com.example.requests;

public enum RequestType {
    AddBook,
    AddUser,
    SelectBooks,
    Invalid;

    public static RequestType fromRequestHeader(String header) {
        switch (header) {
            case "AddBook" -> { return RequestType.AddBook; }
            case "AddUser" -> { return RequestType.AddUser; }
            case "SelectBooks" -> { return RequestType.SelectBooks; }
            default -> { return RequestType.Invalid; }
        }
    }

    public static String toHeader(RequestType type) {
        switch (type) {
            case AddBook -> { return "AddBook"; }
            case AddUser -> { return "AddUser"; }
            case SelectBooks -> { return "SelectBooks"; }
            default -> { return "Invalid"; }
        }
    }

    public String toHeader() {
        return RequestType.toHeader(this);
    }
}
