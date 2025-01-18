package com.example.requests;

public enum RequestType {
    AddBook,
    AddUser,
    AddOrder,
    SelectBooks,
    Invalid;

    public static RequestType fromRequestHeader(String header) {
        switch (header) {
            case "AddBook" -> { return RequestType.AddBook; }
            case "AddUser" -> { return RequestType.AddUser; }
            case "AddOrder" -> { return RequestType.AddOrder; }
            case "SelectBooks" -> { return RequestType.SelectBooks; }
            default -> { return RequestType.Invalid; }
        }
    }

    public static String toRequestHeader(RequestType type) {
        switch (type) {
            case AddBook -> { return "AddBook"; }
            case AddUser -> { return "AddUser"; }
            case AddOrder -> { return "AddOrder"; }
            case SelectBooks -> { return "SelectBooks"; }
            default -> { return "Invalid"; }
        }
    }

    public String toRequestHeader() {
        return RequestType.toRequestHeader(this);
    }
}
