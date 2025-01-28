package com.example.dto;

public enum OrderStatus {
    InPreparation,
    Sent,
    Delivered,
    Invalid;

    public static final OrderStatus initialOrderStatus = InPreparation;

    public static OrderStatus fromString(String str) {
        switch (str) {
            case "InPreparation" -> { return InPreparation; }
            case "Sent" -> { return Sent; }
            case "Delivered" -> { return Delivered; }
        }

        return Invalid;
    }

    public static String toString(OrderStatus status) {
        switch (status) {
            case InPreparation -> { return "InPreparation"; }
            case Sent -> { return "Sent"; }
            case Delivered -> { return "Delivered"; }
        }

        return "Invalid";
    }

    public String toString() {
        return OrderStatus.toString(this);
    }
}
