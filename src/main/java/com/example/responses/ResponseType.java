package com.example.responses;

/**
 * Enum representing the different types of responses that the server can return.
 * <p>
 * Each response type corresponds to a specific status or result of the server's processing of a request.
 * The enum provides methods to map a response type to its corresponding response header string and vice versa.
 * </p>
 *
 * @author Wojciech Opara
 * @version 1.0
 */
public enum ResponseType {
    Ok,
    NotFound,
    Error;

    /**
     * Converts a response header string to a corresponding {@link ResponseType}.
     * <p>
     * This method maps the provided header string (e.g., "Ok") to the corresponding {@link ResponseType}.
     * If the header doesn't match any known response type, it returns {@link ResponseType#Error}.
     * </p>
     *
     * @param header The response header string to be converted.
     * @return The corresponding {@link ResponseType} or {@link ResponseType#Error} if the header is unrecognized.
     */
    public static ResponseType fromResponseHeader(String header) {
        switch (header) {
            case "Ok" -> { return ResponseType.Ok; }
            case "NotFound" -> { return ResponseType.NotFound; }
            default -> { return ResponseType.Error; }
        }
    }

    /**
     * Converts a {@link ResponseType} to its corresponding response header string.
     * <p>
     * This method maps the provided {@link ResponseType} (e.g., {@link ResponseType#Ok}) to the corresponding
     * header string (e.g., "Ok").
     * </p>
     *
     * @param type The {@link ResponseType} to be converted.
     * @return The corresponding response header string.
     */
    public static String toResponseHeader(ResponseType type) {
        switch (type) {
            case Ok -> { return "Ok"; }
            case NotFound -> { return "NotFound"; }
            default -> { return "Error"; }
        }
    }

    /**
     * Converts the current {@link ResponseType} instance to its corresponding response header string.
     * <p>
     * This method uses the {@link ResponseType#toResponseHeader(ResponseType)} method to get the string representation
     * of the current enum instance.
     * </p>
     *
     * @return The corresponding response header string.
     */
    public String toResponseHeader() {
        return ResponseType.toResponseHeader(this);
    }
}
