package com.example.requests;

import com.example.dto.NewOrderDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AddOrderRequest {
    public final NewOrderDto order;

    public AddOrderRequest(NewOrderDto order) {
        this.order = order;
    }

    public AddOrderRequest(String requestContent) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        order = objectMapper.readValue(requestContent, NewOrderDto.class);
    }

    public String create() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(order);
        return Request.create(RequestType.AddOrder, content);
    }
}
