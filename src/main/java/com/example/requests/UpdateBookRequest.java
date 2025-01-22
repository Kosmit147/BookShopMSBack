package com.example.requests;

import com.example.dto.BookDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UpdateBookRequest {
    public final BookDto book;

    public UpdateBookRequest(BookDto book) {
        this.book = book;
    }

    public UpdateBookRequest(String requestContent) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        book = objectMapper.readValue(requestContent, BookDto.class);
    }

    public String create() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(book);
        return Request.create(RequestType.UpdateBook, content);
    }
}
