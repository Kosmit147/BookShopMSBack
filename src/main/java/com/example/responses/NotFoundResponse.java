package com.example.responses;

public class NotFoundResponse {
    public String create() {
        String content = "";
        return Response.create(ResponseType.NotFound, content);
    }
}
