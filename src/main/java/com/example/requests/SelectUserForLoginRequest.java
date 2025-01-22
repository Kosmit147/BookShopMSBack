package com.example.requests;

import com.example.dto.IdDto;
import com.example.dto.UserLoginDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SelectUserForLoginRequest {
    public final UserLoginDto loginData;

    public SelectUserForLoginRequest(UserLoginDto loginData) {
        this.loginData = loginData;
    }

    public SelectUserForLoginRequest(String requestContent) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        loginData = objectMapper.readValue(requestContent, UserLoginDto.class);
    }

    public String create() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(loginData);
        return Request.create(RequestType.SelectUserForLogin, content);
    }
}
