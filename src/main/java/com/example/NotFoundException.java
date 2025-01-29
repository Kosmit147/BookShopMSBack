package com.example;

/**
 * Custom exception class to indicate that a requested resource was not found.
 * <p>
 * This exception can be thrown when a specific item or record cannot be found
 * in the context of database queries or application logic. It extends from {@link Exception}
 * and provides a default message "Not Found" when thrown.
 * </p>
 *
 * @author Wojciech Opara
 * @version 1.0
 */
public class NotFoundException extends Exception {

    /**
     * Constructs a new {@code NotFoundException} with the default message "Not Found".
     */
    public NotFoundException() {
        super("Not Found");
    }
}