package com.example.requests;

public enum RequestType {
    AddBook,
    AddOrder,
    AddRole,
    AddUser,
    AddUserWithRole,
    ChangeOrderStatus,
    DeleteBook,
    DeleteUser,
    SelectBook,
    SelectBooks,
    SelectOrders,
    SelectOrdersForUser,
    SelectUser,
    SelectUserForLogin,
    SelectUsers,
    SelectRoles,
    UpdateBook,
    UpdateUser,
    Invalid;

    public static RequestType fromRequestHeader(String header) {
        switch (header) {
            case "AddBook" -> { return RequestType.AddBook; }
            case "AddOrder" -> { return RequestType.AddOrder; }
            case "AddRole" -> { return RequestType.AddRole; }
            case "AddUser" -> { return RequestType.AddUser; }
            case "AddUserWithRole" -> { return RequestType.AddUserWithRole; }
            case "ChangeOrderStatus" -> { return RequestType.ChangeOrderStatus; }
            case "DeleteBook" -> { return RequestType.DeleteBook; }
            case "DeleteUser" -> { return RequestType.DeleteUser; }
            case "SelectBook" -> { return RequestType.SelectBook; }
            case "SelectBooks" -> { return RequestType.SelectBooks; }
            case "SelectOrders" -> { return RequestType.SelectOrders; }
            case "SelectOrdersForUser" -> { return RequestType.SelectOrdersForUser; }
            case "SelectUser" -> { return RequestType.SelectUser; }
            case "SelectUserForLogin" -> { return RequestType.SelectUserForLogin; }
            case "SelectUsers" -> { return RequestType.SelectUsers; }
            case "SelectRoles" -> { return RequestType.SelectRoles; }
            case "UpdateBook" -> { return RequestType.UpdateBook; }
            case "UpdateUser" -> { return RequestType.UpdateUser; }
            default -> { return RequestType.Invalid; }
        }
    }

    public static String toRequestHeader(RequestType type) {
        switch (type) {
            case AddBook -> { return "AddBook"; }
            case AddOrder -> { return "AddOrder"; }
            case AddRole -> { return "AddRole"; }
            case AddUser -> { return "AddUser"; }
            case AddUserWithRole -> { return "AddUserWithRole"; }
            case ChangeOrderStatus -> { return "ChangeOrderStatus"; }
            case DeleteBook -> { return "DeleteBook"; }
            case DeleteUser -> { return "DeleteUser"; }
            case SelectBook -> { return "SelectBook"; }
            case SelectBooks -> { return "SelectBooks"; }
            case SelectOrders -> { return "SelectOrders"; }
            case SelectOrdersForUser -> { return "SelectOrdersForUser"; }
            case SelectUser -> { return "SelectUser"; }
            case SelectUserForLogin -> { return "SelectUserForLogin"; }
            case SelectUsers -> { return "SelectUsers"; }
            case SelectRoles -> { return "SelectRoles"; }
            case UpdateBook -> { return "UpdateBook"; }
            case UpdateUser -> { return "UpdateUser"; }
            default -> { return "Invalid"; }
        }
    }

    public String toRequestHeader() {
        return RequestType.toRequestHeader(this);
    }
}
