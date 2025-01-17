package com.example;

import com.example.dto.BookDto;

public class AddBookMessage {
    public final BookDto[] books;

    AddBookMessage(String json) {
        // TODO: parse json here
    }
}
