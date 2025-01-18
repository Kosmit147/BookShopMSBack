package com.example.requests;

import com.example.dto.NewUserDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AddUserRequest {
    public final NewUserDto user;

    public AddUserRequest(NewUserDto user) {
        this.user = user;
    }

    public AddUserRequest(String requestContent) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        user = objectMapper.readValue(requestContent, NewUserDto.class);
    }

    public String create() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(user);
        return Request.create(RequestType.AddUser, content);
    }
}
