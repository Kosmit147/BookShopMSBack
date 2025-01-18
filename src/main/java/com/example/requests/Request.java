package com.example.requests;

public class Request {
    public static String create(RequestType type, String content) {
        String header = type.toRequestHeader();
        return header + ":" + content;
    }
}
