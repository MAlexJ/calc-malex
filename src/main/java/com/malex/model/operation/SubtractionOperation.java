package com.malex.model.operation;

import com.malex.model.enums.Operation;
import com.malex.model.operation.AbstractOperation;

import java.math.BigDecimal;

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
