package com.malex.model.exception;

/**
 * Thrown to indicate that a method has no such the arithmetic operation.
 *
 * @author MAlex
 */
public class NoSuchOperationException extends RuntimeException {

    /**
     * Constructs an <code>NoSuchOperationException</code> with the
     * specified detail message.
     *
     * @param message the detail message.
     */
    public NoSuchOperationException(String message) {
        super(message);
    }
}
