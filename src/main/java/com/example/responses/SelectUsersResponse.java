package com.example.responses;

import com.example.dto.UserDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SelectUsersResponse {
    public final UserDto[] users;

    public SelectUsersResponse(UserDto[] users) {
        this.users = users;
    }

    public SelectUsersResponse(String responseContent) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        users = objectMapper.readValue(responseContent, UserDto[].class);
    }

    public String create() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(users);
        return Response.create(ResponseType.Ok, content);
    }
}
