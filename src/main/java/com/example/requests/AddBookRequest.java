package com.example.requests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AddBookRequest {
    public final AddBookDto book;

    public AddBookRequest(AddBookDto book) {
        this.book = book;
    }

    public AddBookRequest(String requestContent) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        book = objectMapper.readValue(requestContent, AddBookDto.class);
    }

    public String create() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(book);
        return Request.create(RequestType.AddBook, content);
    }
}
