package com.malex.model.enums;

import com.malex.model.exception.NoSuchOperationException;

/**
 * Enumeration {@code Operation} stores basic arithmetic operations.
 */
public enum Operation {
    /**
     * The arithmetic operation of addition.
     */
    ADD("ADD"),
    /**
     * The arithmetic operation of subtraction.
     */
    SUBTRACTION("SUBTRACTION"),
    /**
     * The arithmetic operation of division.
     */
    DIVISION("DIVISION"),
    /**
     * The arithmetic operation of multiplication.
     */
    MULTIPLICATION("MULTIPLICATION"),
    /**
     * The arithmetic operation of percentage.
     */
    PERCENT("PERCENT");

    /**
     * Value is used to store the name arithmetic operation.
     */
    private String description;

    /**
     * Get enumeration: ADD, SUBTRACTION, DIVISION, MULTIPLICATION, PERCENT.
     *
     * @param description the name arithmetic operation.
     */
    Operation(String description) {
        this.description = description;
    }

    /**
     * Get the value of arithmetic operation.
     *
     * @param description the name arithmetic operation.
     * @return the value operation.
     * @throws NoSuchOperationException if arithmetic operation doesn't exist.
     */
    public static Operation get(String description) {
        for (Operation name : Operation.values()) {
            if (name.description.equals(description)) {
                return name;
            }
        }
        throw new NoSuchOperationException("Operation: \'" + description + "\' with this name doesn't exist!");
    }
}
