package com.example.requests;

import com.example.dto.IdDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SelectBookRequest {
    public final IdDto bookId;

    public SelectBookRequest(IdDto bookId) {
        this.bookId = bookId;
    }

    public SelectBookRequest(String requestContent) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        bookId = objectMapper.readValue(requestContent, IdDto.class);
    }

    public String create() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(bookId);
        return Request.create(RequestType.SelectBook, content);
    }
}
