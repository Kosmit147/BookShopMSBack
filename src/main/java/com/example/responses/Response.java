package com.example.responses;

public class Response {
    public static String create(ResponseType type, String content) {
        String header = type.toResponseHeader();
        return header + ":" + content;
    }
}
