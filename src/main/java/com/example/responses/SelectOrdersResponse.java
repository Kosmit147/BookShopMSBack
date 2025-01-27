package com.example.responses;

import com.example.dto.OrderDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SelectOrdersResponse {
    public final OrderDto[] orders;

    public SelectOrdersResponse(OrderDto[] orders) {
        this.orders = orders;
    }

    public SelectOrdersResponse(String responseContent) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        orders = objectMapper.readValue(responseContent, OrderDto[].class);
    }

    public String create() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(orders);
        return Response.create(ResponseType.Ok, content);
    }
}
