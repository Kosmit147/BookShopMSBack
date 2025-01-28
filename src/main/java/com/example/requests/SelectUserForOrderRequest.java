package com.example.requests;

import com.example.dto.IdDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SelectUserForOrderRequest {
    public final IdDto orderId;

    public SelectUserForOrderRequest(IdDto orderId) {
        this.orderId = orderId;
    }

    public SelectUserForOrderRequest(String requestContent) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        orderId = objectMapper.readValue(requestContent, IdDto.class);
    }

    public String create() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(orderId);
        return Request.create(RequestType.SelectUserForOrder, content);
    }
}
