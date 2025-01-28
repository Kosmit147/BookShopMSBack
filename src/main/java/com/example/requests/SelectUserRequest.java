package com.example.requests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SelectUserRequest {
    public final int id;

    public SelectUserRequest(int id) {
        this.id = id;
    }

    public SelectUserRequest(String requestContent) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        id = objectMapper.readValue(requestContent, int.class);
    }

    public String create() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(id);
        return Request.create(RequestType.SelectBook, content);
    }
}
