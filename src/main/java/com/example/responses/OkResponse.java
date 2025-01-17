package com.example.responses;

public class OkResponse {
    public String create() {
        String content = "";
        return Response.create(ResponseType.Ok, content);
    }
}
