package com.example.requests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SelectBooksForOrderRequest {
    public final int orderId;

    public SelectBooksForOrderRequest(int orderId) {
        this.orderId = orderId;
    }

    public SelectBooksForOrderRequest(String requestContent) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        orderId = objectMapper.readValue(requestContent, int.class);
    }

    public String create() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(orderId);
        return Request.create(RequestType.SelectBooksForOrder, content);
    }
}
