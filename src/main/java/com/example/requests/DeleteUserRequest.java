package com.example.requests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DeleteUserRequest {
    public final int id;

    public DeleteUserRequest(int id) {
        this.id = id;
    }

    public DeleteUserRequest(String requestContent) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        id = objectMapper.readValue(requestContent, int.class);
    }

    public String create() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(id);
        return Request.create(RequestType.DeleteUser, content);
    }
}
