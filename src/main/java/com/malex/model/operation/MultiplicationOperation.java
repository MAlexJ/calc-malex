package com.malex.model.operation;

import com.malex.model.enums.Operation;

import java.math.BigDecimal;

/**
 * The {@code MultiplicationOperation} class represents an algorithm for multiplying two numbers.
 */
public class MultiplicationOperation extends AbstractOperation {

    /**
     * Initializes a newly created {@code MultiplicationOperation} object.
     * {@code Operation}
     */
    public MultiplicationOperation() {
        super(Operation.MULTIPLICATION);
    }

    /**
     * Execute the multiply operation of two numbers.
     *
     * @param numberOne the first number.
     * @param numberTwo the second number.
     * @return the result of the multiply operation of two numbers.
     */
    @Override
    public BigDecimal execute(BigDecimal numberOne, BigDecimal numberTwo) {
        return numberOne.multiply(numberTwo, MATH_CONTEXT);
    }
}
