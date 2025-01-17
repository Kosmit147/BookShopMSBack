package com.example.requests;

import com.example.dto.BookDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AddBookRequest {
    public final BookDto book;

    public AddBookRequest(BookDto book) {
        this.book = book;
    }

    public AddBookRequest(String requestContent) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        book = objectMapper.readValue(requestContent, BookDto.class);
    }

    public String create() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(book);
        return Request.create(RequestType.AddBook, content);
    }
}
