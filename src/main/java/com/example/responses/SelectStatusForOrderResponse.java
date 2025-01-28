package com.example.responses;

import com.example.dto.OrderStatus;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SelectStatusForOrderResponse {
    public final OrderStatus status;

    public SelectStatusForOrderResponse(OrderStatus status) {
        this.status = status;
    }

    public SelectStatusForOrderResponse(String responseContent) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        status = objectMapper.readValue(responseContent, OrderStatus.class);
    }

    public String create() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(status);
        return Response.create(ResponseType.Ok, content);
    }
}
