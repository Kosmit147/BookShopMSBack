package com.example.requests;

public class SelectRolesRequest {
    public String create() {
        return Request.create(RequestType.SelectRoles, "");
    }
}
