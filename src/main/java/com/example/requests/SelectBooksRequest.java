package com.example.requests;

public class SelectBooksRequest {
    public String create() {
        return Request.create(RequestType.SelectBooks, "");
    }
}
