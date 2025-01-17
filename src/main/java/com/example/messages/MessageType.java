package com.example.messages;

public enum MessageType {
    AddBook,
    AddUser,
    Invalid;

    public static MessageType fromMessageHeader(String header) {
        switch (header) {
            case "AddBook" -> { return MessageType.AddBook; }
            case "AddUser" -> { return MessageType.AddUser; }
            default -> { return MessageType.Invalid; }
        }
    }

    public String toHeader() {
        switch (this) {
            case AddBook -> { return "AddBook"; }
            case AddUser -> { return "AddUser"; }
            default -> { return "Invalid"; }
        }
    }
}
