package com.example.responses;

import com.example.dto.RoleDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SelectRolesResponse {
    public final RoleDto[] roles;

    public SelectRolesResponse(RoleDto[] users) {
        this.roles = users;
    }

    public SelectRolesResponse(String responseContent) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        roles = objectMapper.readValue(responseContent, RoleDto[].class);
    }

    public String create() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(roles);
        return Response.create(ResponseType.Ok, content);
    }
}
