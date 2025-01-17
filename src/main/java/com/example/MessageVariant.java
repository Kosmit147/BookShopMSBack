package com.example.messages;

public sealed interface MessageVariant permits MessageVariant.AddBookMessageValue {
    record AddBookMessageValue(AddBookMessage addBookMessage) implements MessageVariant {}
}
