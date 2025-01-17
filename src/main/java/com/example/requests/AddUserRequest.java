package com.example.requests;

import com.example.dto.AddUserDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AddUserRequest {
    public final AddUserDto user;

    public AddUserRequest(AddUserDto user) {
        this.user = user;
    }

    public AddUserRequest(String requestContent) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        user = objectMapper.readValue(requestContent, AddUserDto.class);
    }

    public String create() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(user);
        return Request.create(RequestType.AddUser, content);
    }
}
