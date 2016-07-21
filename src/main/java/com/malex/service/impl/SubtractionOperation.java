package com.malex.service.impl;

import com.malex.model.enums.OperationName;
import com.malex.service.AbstractOperation;

import java.math.BigDecimal;

public class SubtractionOperation extends AbstractOperation {

    /**
     * Initializes a newly created {@code SubtractionOperation} object.
     * {@code OperationName}
     */
    public SubtractionOperation() {
        super(OperationName.SUBTRACTION);
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
