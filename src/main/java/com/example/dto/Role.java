package com.example.dto;

public enum Role {
    User,
    Admin,
    Invalid;

    public static final Role initialUserRole = User;

    public static Role fromString(String str) {
        switch (str) {
            case "User" -> { return User; }
            case "Admin" -> { return Admin; }
        }

        return Invalid;
    }

    public static String toString(Role role) {
        switch (role) {
            case User -> { return "User"; }
            case Admin -> { return "Admin"; }
        }

        return "Invalid";
    }

    public String toString() {
        return Role.toString(this);
    }
}
