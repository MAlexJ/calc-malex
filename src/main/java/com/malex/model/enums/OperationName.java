package com.malex.model.enums;

import com.malex.model.exception.NoSuchOperationException;

/**
 * Enumeration {@code OperationName} stores basic arithmetic operations.
 */
public enum OperationName {
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
     * @param description the name arithmetic operation.
     */
    OperationName(String description) {
        this.description = description;
    }

    /**
     * Get the value of arithmetic operation.
     *
     * @param description the name arithmetic operation.
     * @return the value operation.
     * @throws NoSuchOperationException if arithmetic operation doesn't exist.
     */
    public static OperationName get(String description) {
        for (OperationName name : OperationName.values()) {
            if (name.description.equals(description)) {
                return name;
            }
        }
        throw new NoSuchOperationException("OperationName: \'" + description + "\' with this name doesn't exist!");
    }
}
