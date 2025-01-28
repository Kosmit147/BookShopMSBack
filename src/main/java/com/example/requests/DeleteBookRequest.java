package com.example.requests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DeleteBookRequest {
    public final int id;

    public DeleteBookRequest(int id) {
        this.id = id;
    }

    public DeleteBookRequest(String requestContent) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        id = objectMapper.readValue(requestContent, int.class);
    }

    public String create() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(id);
        return Request.create(RequestType.DeleteBook, content);
    }
}
