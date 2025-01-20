package com.example.requests;

public class SelectUsersRequest {
    public String create() {
        return Request.create(RequestType.SelectUsers, "");
    }
}
