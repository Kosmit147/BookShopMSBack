package com.example.requests;

public sealed interface RequestVariant permits
        RequestVariant.AddBookRequestValue,
        RequestVariant.AddUserRequestValue,
        RequestVariant.InvalidRequestValue {
    record AddBookRequestValue(AddBookRequest value) implements RequestVariant {}
    record AddUserRequestValue(AddUserRequest value) implements RequestVariant {}
    record InvalidRequestValue() implements RequestVariant {}
}
