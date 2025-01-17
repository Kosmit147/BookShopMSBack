package com.example.requests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AddBookRequest {
    public final AddBookDto book;

    public AddBookRequest(AddBookDto book) {
        this.book = book;
    }

    public AddBookRequest(String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        book = objectMapper.readValue(json, AddBookDto.class);
    }

    public String deserialize() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(book);
    }
}
