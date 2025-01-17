package com.example.requests;

public sealed interface RequestVariant permits
        RequestVariant.AddBookRequestValue,
        RequestVariant.AddUserRequestValue,
        RequestVariant.SelectBooksRequestValue,
        RequestVariant.InvalidRequestValue {
    record AddBookRequestValue(AddBookRequest value) implements RequestVariant {}
    record AddUserRequestValue(AddUserRequest value) implements RequestVariant {}
    record SelectBooksRequestValue(SelectBooksRequest value) implements RequestVariant {}
    record InvalidRequestValue() implements RequestVariant {}
}
