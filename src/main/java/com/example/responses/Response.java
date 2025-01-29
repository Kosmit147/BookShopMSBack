package com.example.responses;

/**
 * A utility class for handling responses, including the creation and splitting of response strings.
 * <p>
 * This class provides methods for generating a formatted response string from a {@link ResponseType}
 * and content, as well as splitting a full response string into its header and content components.
 * </p>
 *
 * @author Wojciech Opara
 * @version 1.0
 */
public class Response {

    /**
     * Creates a response string by combining a {@link ResponseType} header and content.
     * <p>
     * The generated response string is formatted as:
     * <code>{responseType}:{content}</code>
     * </p>
     *
     * @param type The type of the response, used to generate the response header.
     * @param content The content associated with the response.
     * @return A string representing the formatted response, combining the type header and content.
     */
    public static String create(ResponseType type, String content) {
        String header = type.toResponseHeader();
        return header + ":" + content;
    }

    /**
     * Splits a full response string into its header and content components.
     * <p>
     * The response string is split by the first occurrence of the colon (":") character, where:
     * - The part before the colon is considered the response header.
     * - The part after the colon is considered the content.
     * </p>
     *
     * @param fullResponse The full response string to be split.
     * @return An array of two strings: the response header and the content.
     */
    public static String[] split(String fullResponse) {
        String[] parts = fullResponse.split(":", 2);
        String header = parts[0];
        String content = parts.length >= 2 ? parts[1] : "";
        return new String[]{header, content};
    }
}
