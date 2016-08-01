package com.malex.model.operation;

import com.malex.model.enums.Operation;

import java.math.BigDecimal;

/**
 * The {@code AdditionOperation} class represents the summation algorithm of two numbers.
 */
public class AdditionOperation extends AbstractOperation {

    /**
     * Initializes a newly created {@code AdditionOperation} object.
     * {@code Operation}
     */
    public AdditionOperation() {
        super(Operation.ADDITION);
    }

    /**
     * Execute the addition operation of two numbers.
     *
     * @param numberOne the first number.
     * @param numberTwo the second number.
     * @return the result of the addition operation of two numbers.
     */
    @Override
    public BigDecimal execute(BigDecimal numberOne, BigDecimal numberTwo) {
        return numberOne.add(numberTwo, MATH_CONTEXT);
    }
}
