package com.example.requests;

import com.example.dto.ChangeOrderStatusDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ChangeOrderStatusRequest {
    public final ChangeOrderStatusDto changeOrderStatus;

    public ChangeOrderStatusRequest(ChangeOrderStatusDto changeOrderStatus) {
        this.changeOrderStatus = changeOrderStatus;
    }

    public ChangeOrderStatusRequest(String requestContent) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        changeOrderStatus = objectMapper.readValue(requestContent, ChangeOrderStatusDto.class);
    }

    public String create() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(changeOrderStatus);
        return Request.create(RequestType.ChangeOrderStatus, content);
    }
}
