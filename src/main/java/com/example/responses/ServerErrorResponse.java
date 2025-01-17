package com.example.responses;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ServerErrorResponse {
    public final ServerErrorDto serverError;

    public ServerErrorResponse(ServerErrorDto serverError) {
        this.serverError = serverError;
    }

    public ServerErrorResponse(String responseContent) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        serverError = objectMapper.readValue(responseContent, ServerErrorDto.class);
    }

    public String create() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String content = objectMapper.writeValueAsString(serverError);
            return Response.create(ResponseType.ServerError, content);
        } catch (JsonProcessingException e) {
            return Response.create(ResponseType.ServerError, "Internal Server Error");
        }
    }
}
