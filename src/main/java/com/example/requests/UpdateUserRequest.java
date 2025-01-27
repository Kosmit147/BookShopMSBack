package com.example.requests;

import com.example.dto.UserDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UpdateUserRequest {
    public final UserDto user;

    public UpdateUserRequest(UserDto user) {
        this.user = user;
    }

    public UpdateUserRequest(String requestContent) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        user = objectMapper.readValue(requestContent, UserDto.class);
    }

    public String create() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(user);
        return Request.create(RequestType.UpdateUser, content);
    }
}
