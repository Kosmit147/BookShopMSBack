package com.example;

import com.example.messages.AddBookMessage;

sealed interface MessageVariant permits MessageVariant.AddBookMessageValue, MessageVariant.InvalidMessageValue {
    record AddBookMessageValue(AddBookMessage value) implements MessageVariant {}
    record InvalidMessageValue() implements MessageVariant {}
}
