package com.malex.service.impl;

import com.malex.model.enums.OperationName;
import com.malex.service.AbstractOperation;

import java.math.BigDecimal;

public class MultiplicationOperation extends AbstractOperation {

    /**
     * Initializes a newly created {@code MultiplicationOperation} object.
     * {@code OperationName}
     */
    public MultiplicationOperation() {
        super(OperationName.MULTIPLICATION);
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
        return numberOne.multiply(numberTwo);
    }
}
