package com.example.messages;

public sealed interface MessageVariant permits
        MessageVariant.AddBookMessageValue,
        MessageVariant.AddUserMessageValue,
        MessageVariant.InvalidMessageValue {
    record AddBookMessageValue(AddBookMessage value) implements MessageVariant {}
    record AddUserMessageValue(AddUserMessage value) implements MessageVariant {}
    record InvalidMessageValue() implements MessageVariant {}
}
