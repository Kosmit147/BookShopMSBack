package com.example.requests;

import com.example.dto.UpdateOrderStatusDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UpdateOrderStatusRequest {
    public final UpdateOrderStatusDto updateOrderStatus;

    public UpdateOrderStatusRequest(UpdateOrderStatusDto updateOrderStatus) {
        this.updateOrderStatus = updateOrderStatus;
    }

    public UpdateOrderStatusRequest(String requestContent) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        updateOrderStatus = objectMapper.readValue(requestContent, UpdateOrderStatusDto.class);
    }

    public String create() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(updateOrderStatus);
        return Request.create(RequestType.UpdateOrderStatus, content);
    }
}
