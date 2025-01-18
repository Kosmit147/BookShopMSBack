package com.example.responses;

import com.example.dto.BookDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SelectBookResponse {
    public final BookDto book;

    public SelectBookResponse(BookDto book) {
        this.book = book;
    }

    public SelectBookResponse(String responseContent) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        book = objectMapper.readValue(responseContent, BookDto.class);
    }

    public String create() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(book);
        return Response.create(ResponseType.Ok, content);
    }
}
