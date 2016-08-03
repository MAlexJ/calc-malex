package com.malex.model.enums;

/**
 * Enumeration {@code Operation} stores basic arithmetic operations.
 */
public enum Operation {
    /**
     * The arithmetic operation of addition.
     */
    ADDITION("addition"),
    /**
     * The arithmetic operation of subtraction.
     */
    SUBTRACTION("subtraction"),
    /**
     * The arithmetic operation of division.
     */
    DIVISION("division"),
    /**
     * The arithmetic operation of multiplication.
     */
    MULTIPLICATION("multiplication"),
    /**
     * The arithmetic operation of percentage.
     */
    PERCENT("percent"),
    /**
     * The arithmetic operation of equals.
     */
    EQUALS("equals");

    /**
     * Value is used to store the name arithmetic operation.
     */
    private String description;

    /**
     * Get enumeration: addition, subtraction, division, multiplication, percent.
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
     * @throws IllegalArgumentException if arithmetic operation doesn't exist.
     */
    public static Operation get(String description) {
        for (Operation name : Operation.values()) {
            if (name.description.equals(description)) {
                return name;
            }
        }
        throw new IllegalArgumentException("Operation: \'" + description + "\' with this name doesn't exist!");
    }
}
