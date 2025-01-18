package com.example.requests;

import com.example.dto.UpdateCartDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UpdateCartRequest {
    public final UpdateCartDto cart;

    public UpdateCartRequest(UpdateCartDto cart) {
        this.cart = cart;
    }

    public UpdateCartRequest(String requestContent) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        cart = objectMapper.readValue(requestContent, UpdateCartDto.class);
    }

    public String create() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(cart);
        return Request.create(RequestType.UpdateCart, content);
    }
}
