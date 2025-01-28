package com.example.responses;

import com.example.dto.ErrorDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ErrorResponse {
    public final ErrorDto error;

    public ErrorResponse(ErrorDto error) {
        this.error = error;
    }

    public ErrorResponse(String responseContent) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        error = objectMapper.readValue(responseContent, ErrorDto.class);
    }

    public String create() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String content = objectMapper.writeValueAsString(error);
            return Response.create(ResponseType.Error, content);
        } catch (JsonProcessingException e) {
            return Response.create(ResponseType.Error, "Internal Server Error");
        }
    }
}
