package com.example.responses;

public class Response {
    public static String create(ResponseType type, String content) {
        String header = type.toResponseHeader();
        return header + ":" + content;
    }

    public static String[] split(String fullResponse) {
        String[] parts = fullResponse.split(":", 2);
        String header = parts[0];
        String content = parts.length >= 2 ? parts[1] : "";
        return new String[]{header, content};
    }
}
