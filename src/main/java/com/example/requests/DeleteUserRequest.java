package com.example.requests;

import com.example.dto.IdDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DeleteUserRequest {
    public final IdDto id;

    public DeleteUserRequest(IdDto id) {
        this.id = id;
    }

    public DeleteUserRequest(String requestContent) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        id = objectMapper.readValue(requestContent, IdDto.class);
    }

    public String create() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(id);
        return Request.create(RequestType.DeleteUser, content);
    }
}
