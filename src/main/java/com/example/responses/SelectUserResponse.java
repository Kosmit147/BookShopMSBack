package com.example.responses;

import com.example.dto.UserDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SelectUserResponse {
    public final UserDto user;

    public SelectUserResponse(UserDto user) {
        this.user = user;
    }

    public SelectUserResponse(String responseContent) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        user = objectMapper.readValue(responseContent, UserDto.class);
    }

    public String create() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(user);
        return Response.create(ResponseType.Ok, content);
    }
}
