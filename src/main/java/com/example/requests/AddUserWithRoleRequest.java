package com.example.requests;

import com.example.dto.NewUserWithRoleDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AddUserWithRoleRequest {
    public final NewUserWithRoleDto user;

    public AddUserWithRoleRequest(NewUserWithRoleDto user) {
        this.user = user;
    }

    public AddUserWithRoleRequest(String requestContent) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        user = objectMapper.readValue(requestContent, NewUserWithRoleDto.class);
    }

    public String create() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(user);
        return Request.create(RequestType.AddUserWithRole, content);
    }
}
