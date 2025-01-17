package com.example.responses;

public sealed interface ResponseVariant permits
        ResponseVariant.OkResponseValue,
        ResponseVariant.ServerErrorResponseValue {
    record OkResponseValue(OkResponse value) implements ResponseVariant {}
    record ServerErrorResponseValue(ServerErrorResponse value) implements ResponseVariant {}
}
