package com.malex.service.impl;

import com.malex.model.enums.OperationName;
import com.malex.service.AbstractOperation;

import java.math.BigDecimal;

public class AddOperation extends AbstractOperation {

    /**
     * Initializes a newly created {@code AddOperation} object.
     * {@code OperationName}
     */
    public AddOperation() {
        super(OperationName.ADD);
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
        return numberOne.add(numberTwo);
    }
}
