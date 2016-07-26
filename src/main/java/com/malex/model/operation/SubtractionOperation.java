package com.malex.model.operation;

import com.malex.model.enums.Operation;

import java.math.BigDecimal;

/**
 * The {@code SubtractionOperation} class represents the algorithm of subtracting two numbers.
 */
public class SubtractionOperation extends AbstractOperation {

    /**
     * Initializes a newly created {@code SubtractionOperation} object.
     * {@code Operation}
     */
    public SubtractionOperation() {
        super(Operation.SUBTRACTION);
    }

    /**
     * Execute the subtract operation of two numbers.
     *
     * @param numberOne the first number.
     * @param numberTwo the second number.
     * @return the result of the subtract operation of two numbers.
     */
    @Override
    public BigDecimal execute(BigDecimal numberOne, BigDecimal numberTwo) {
        return numberOne.subtract(numberTwo);
    }
}
