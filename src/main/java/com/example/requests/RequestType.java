package com.example.requests;

/**
 * Enum representing the different types of requests that the server can handle.
 * <p>
 * Each request type corresponds to a specific action or operation that can be requested by the client.
 * The enum provides methods to map a request type to its corresponding request header string and vice versa.
 * </p>
 * <p>
 * This enum also includes an {@code Invalid} request type for handling unrecognized request headers.
 * </p>
 *
 * @author Wojciech Opara
 * @version 1.0
 */
public enum RequestType {
    AddBook,
    AddOrder,
    AddUser,
    AddUserWithRole,
    DeleteBook,
    DeleteUser,
    SelectBook,
    SelectBooks,
    SelectBooksForOrder,
    SelectOrders,
    SelectOrdersForUser,
    SelectStatusForOrder,
    SelectUser,
    SelectUserForLogin,
    SelectUserForOrder,
    SelectUsers,
    UpdateBook,
    UpdateOrderStatus,
    UpdateUser,
    Invalid;

    /**
     * Converts a request header string to a corresponding {@link RequestType}.
     * <p>
     * This method maps the provided header string (e.g., "AddBook") to the corresponding {@link RequestType}.
     * If the header doesn't match any known request type, it returns {@link RequestType#Invalid}.
     * </p>
     *
     * @param header The request header string to be converted.
     * @return The corresponding {@link RequestType} or {@link RequestType#Invalid} if the header is unrecognized.
     */
    public static RequestType fromRequestHeader(String header) {
        switch (header) {
            case "AddBook" -> { return RequestType.AddBook; }
            case "AddOrder" -> { return RequestType.AddOrder; }
            case "AddUser" -> { return RequestType.AddUser; }
            case "AddUserWithRole" -> { return RequestType.AddUserWithRole; }
            case "DeleteBook" -> { return RequestType.DeleteBook; }
            case "DeleteUser" -> { return RequestType.DeleteUser; }
            case "SelectBook" -> { return RequestType.SelectBook; }
            case "SelectBooks" -> { return RequestType.SelectBooks; }
            case "SelectBooksForOrder" -> { return RequestType.SelectBooksForOrder; }
            case "SelectOrders" -> { return RequestType.SelectOrders; }
            case "SelectOrdersForUser" -> { return RequestType.SelectOrdersForUser; }
            case "SelectStatusForOrder" -> { return RequestType.SelectStatusForOrder; }
            case "SelectUser" -> { return RequestType.SelectUser; }
            case "SelectUserForLogin" -> { return RequestType.SelectUserForLogin; }
            case "SelectUserForOrder" -> { return RequestType.SelectUserForOrder; }
            case "SelectUsers" -> { return RequestType.SelectUsers; }
            case "UpdateBook" -> { return RequestType.UpdateBook; }
            case "UpdateOrderStatus" -> { return RequestType.UpdateOrderStatus; }
            case "UpdateUser" -> { return RequestType.UpdateUser; }
            default -> { return RequestType.Invalid; }
        }
    }

    /**
     * Converts a {@link RequestType} to its corresponding request header string.
     * <p>
     * This method maps the provided {@link RequestType} (e.g., {@link RequestType#AddBook}) to the corresponding
     * header string (e.g., "AddBook").
     * </p>
     *
     * @param type The {@link RequestType} to be converted.
     * @return The corresponding request header string.
     */
    public static String toRequestHeader(RequestType type) {
        switch (type) {
            case AddBook -> { return "AddBook"; }
            case AddOrder -> { return "AddOrder"; }
            case AddUser -> { return "AddUser"; }
            case AddUserWithRole -> { return "AddUserWithRole"; }
            case DeleteBook -> { return "DeleteBook"; }
            case DeleteUser -> { return "DeleteUser"; }
            case SelectBook -> { return "SelectBook"; }
            case SelectBooks -> { return "SelectBooks"; }
            case SelectBooksForOrder -> { return "SelectBooksForOrder"; }
            case SelectOrders -> { return "SelectOrders"; }
            case SelectOrdersForUser -> { return "SelectOrdersForUser"; }
            case SelectStatusForOrder -> { return "SelectStatusForOrder"; }
            case SelectUser -> { return "SelectUser"; }
            case SelectUserForLogin -> { return "SelectUserForLogin"; }
            case SelectUserForOrder -> { return "SelectUserForOrder"; }
            case SelectUsers -> { return "SelectUsers"; }
            case UpdateBook -> { return "UpdateBook"; }
            case UpdateOrderStatus -> { return "UpdateOrderStatus"; }
            case UpdateUser -> { return "UpdateUser"; }
            default -> { return "Invalid"; }
        }
    }

    /**
     * Converts the current {@link RequestType} instance to its corresponding request header string.
     * <p>
     * This method uses the {@link RequestType#toRequestHeader(RequestType)} method to get the string representation
     * of the current enum instance.
     * </p>
     *
     * @return The corresponding request header string.
     */
    public String toRequestHeader() {
        return RequestType.toRequestHeader(this);
    }
}
