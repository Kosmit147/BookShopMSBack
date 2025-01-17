package com.example.responses;

import com.example.dto.BookDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SelectBooksResponse {
    public final BookDto[] books;

    public SelectBooksResponse(BookDto[] books) {
        this.books = books;
    }

    public SelectBooksResponse(String responseContent) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        books = objectMapper.readValue(responseContent, BookDto[].class);
    }

    public String create() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(books);
        return Response.create(ResponseType.Ok, content);
    }
}
