package com.example.requests;

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

    public String toRequestHeader() {
        return RequestType.toRequestHeader(this);
    }
}
