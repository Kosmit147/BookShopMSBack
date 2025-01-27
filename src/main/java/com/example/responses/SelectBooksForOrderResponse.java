package com.example.responses;

import com.example.dto.BookOrderDetailsDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SelectBooksForOrderResponse {
    public final BookOrderDetailsDto[] books;

    public SelectBooksForOrderResponse(BookOrderDetailsDto[] books) {
        this.books = books;
    }

    public SelectBooksForOrderResponse(String responseContent) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        books = objectMapper.readValue(responseContent, BookOrderDetailsDto[].class);
    }

    public String create() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(books);
        return Response.create(ResponseType.Ok, content);
    }
}
