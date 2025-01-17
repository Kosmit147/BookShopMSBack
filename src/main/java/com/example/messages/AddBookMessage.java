package com.example.messages;

import com.example.dto.BookDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AddBookMessage {
    public final BookDto book;

    public AddBookMessage(BookDto book) {
        this.book = book;
    }

    public AddBookMessage(String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        book = objectMapper.readValue(json, BookDto.class);
    }

    public String deserialize() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(book);
    }
}
