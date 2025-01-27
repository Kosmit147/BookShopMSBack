package com.example.requests;

import com.example.dto.NewRoleDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AddRoleRequest {
    public final NewRoleDto role;

    public AddRoleRequest(NewRoleDto role) {
        this.role = role;
    }

    public AddRoleRequest(String requestContent) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        role = objectMapper.readValue(requestContent, NewRoleDto.class);
    }

    public String create() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(role);
        return Request.create(RequestType.AddRole, content);
    }
}
