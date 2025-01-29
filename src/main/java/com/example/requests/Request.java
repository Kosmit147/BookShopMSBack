package com.example.requests;

/**
 * A utility class for creating request strings.
 * <p>
 * This class provides a static method to generate a formatted request string,
 * which consists of a header derived from a {@link RequestType} and a content string.
 * </p>
 * <p>
 * The class allows creating a request format that can be used for communication
 * between clients and servers, where each request consists of a type header and
 * its associated content.
 * </p>
 *
 * @author Wojciech Opara
 * @version 1.0
 */
public class Request {

    /**
     * Creates a request string by combining a {@link RequestType} header and content.
     * <p>
     * The generated request string is formatted as:
     * <code>{requestType}:{content}</code>
     * </p>
     *
     * @param type The type of the request, used to generate the request header.
     * @param content The content associated with the request.
     * @return A string representing the formatted request, combining the type header and content.
     */
    public static String create(RequestType type, String content) {
        String header = type.toRequestHeader();
        return header + ":" + content;
    }
}
