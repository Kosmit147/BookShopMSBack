package com.example.requests;

public enum RequestType {
    AddBook,
    AddUser,
    AddUserWithRole,
    AddOrder,
    SelectBook,
    SelectBooks,
    SelectUser,
    SelectUserForLogin,
    SelectUsers,
    Invalid;

    public static RequestType fromRequestHeader(String header) {
        switch (header) {
            case "AddBook" -> { return RequestType.AddBook; }
            case "AddUser" -> { return RequestType.AddUser; }
            case "AddUserWithRole" -> { return RequestType.AddUserWithRole; }
            case "AddOrder" -> { return RequestType.AddOrder; }
            case "SelectBook" -> { return RequestType.SelectBook; }
            case "SelectBooks" -> { return RequestType.SelectBooks; }
            case "SelectUser" -> { return RequestType.SelectUser; }
            case "SelectUserForLogin" -> { return RequestType.SelectUserForLogin; }
            case "SelectUsers" -> { return RequestType.SelectUsers; }
            default -> { return RequestType.Invalid; }
        }
    }

    public static String toRequestHeader(RequestType type) {
        switch (type) {
            case AddBook -> { return "AddBook"; }
            case AddUser -> { return "AddUser"; }
            case AddUserWithRole -> { return "AddUserWithRole"; }
            case AddOrder -> { return "AddOrder"; }
            case SelectBook -> { return "SelectBook"; }
            case SelectBooks -> { return "SelectBooks"; }
            case SelectUser -> { return "SelectUser"; }
            case SelectUserForLogin -> { return "SelectUserForLogin"; }
            case SelectUsers -> { return "SelectUsers"; }
            default -> { return "Invalid"; }
        }
    }

    public String toRequestHeader() {
        return RequestType.toRequestHeader(this);
    }
}
