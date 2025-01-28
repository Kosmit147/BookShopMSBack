package com.example.requests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SelectOrdersForUserRequest {
    public final int userId;

    public SelectOrdersForUserRequest(int userId) {
        this.userId = userId;
    }

    public SelectOrdersForUserRequest(String requestContent) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        userId = objectMapper.readValue(requestContent, int.class);
    }

    public String create() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(userId);
        return Request.create(RequestType.SelectOrdersForUser, content);
    }
}
