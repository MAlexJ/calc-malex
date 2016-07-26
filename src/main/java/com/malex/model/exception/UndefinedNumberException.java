package com.malex.model.exception;

/**
 * Thrown to indicate that the method has the operation of division by zero.
 * Indicate that value is undefined.
 *
 * @author MAlex
 */
public class UndefinedNumberException extends Exception {

    /**
     * Constructs an <code>UndefinedNumberException</code> with the
     * specified detail message.
     *
     * @param message the detail message.
     */
    public UndefinedNumberException(String message) {
        super(message);
    }
}
