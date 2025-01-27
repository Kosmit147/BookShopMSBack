package com.example.requests;

public class SelectOrdersRequest {
    public String create() {
        return Request.create(RequestType.SelectOrders, "");
    }
}
