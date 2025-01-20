package com.example.responses;

import com.example.dto.StringDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ErrorResponse {
    public final StringDto errorDescription;

    public ErrorResponse(StringDto errorDescription) {
        this.errorDescription = errorDescription;
    }

    public ErrorResponse(String responseContent) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        errorDescription = objectMapper.readValue(responseContent, StringDto.class);
    }

    public String create() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String content = objectMapper.writeValueAsString(errorDescription);
            return Response.create(ResponseType.Error, content);
        } catch (JsonProcessingException e) {
            return Response.create(ResponseType.Error, "Internal Server Error");
        }
    }
}
