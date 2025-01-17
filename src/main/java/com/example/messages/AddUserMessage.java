package com.example.messages;

import com.example.dto.UserDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AddUserMessage {
    public final UserDto user;

    public AddUserMessage(UserDto user) {
        this.user = user;
    }

    public AddUserMessage(String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        user = objectMapper.readValue(json, UserDto.class);
    }

    public String deserialize() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(user);
    }
}
