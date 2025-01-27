package com.example.requests;

import com.example.dto.IdDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SelectOrdersForUserRequest {
    public final IdDto userId;

    public SelectOrdersForUserRequest(IdDto userId) {
        this.userId = userId;
    }

    public SelectOrdersForUserRequest(String requestContent) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        userId = objectMapper.readValue(requestContent, IdDto.class);
    }

    public String create() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(userId);
        return Request.create(RequestType.SelectOrdersForUser, content);
    }
}
