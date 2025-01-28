package com.example.requests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SelectStatusForOrderRequest {
    public final int orderId;

    public SelectStatusForOrderRequest(int orderId) {
        this.orderId = orderId;
    }

    public SelectStatusForOrderRequest(String requestContent) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        orderId = objectMapper.readValue(requestContent, int.class);
    }

    public String create() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(orderId);
        return Request.create(RequestType.SelectStatusForOrder, content);
    }
}
