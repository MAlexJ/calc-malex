package com.malex.model.operation.impl;

import com.malex.model.enums.Operation;
import com.malex.model.exception.UndefinedNumberException;
import com.malex.model.operation.AbstractOperation;

import java.math.BigDecimal;

public class DivisionOperation extends AbstractOperation {

    /**
     * Value is used to store scale of the number.
     */
    private static final int SCALE = 14;

    /**
     * Initializes a newly created {@code DivisionOperation} object.
     * {@code Operation}
     */
    public DivisionOperation() {
        super(Operation.DIVISION);
    }

    /**
     * Execute the divide operation of two numbers.
     * Round number: {@code BigDecimal.ROUND_HALF_UP}
     *
     * @param numberOne the first number.
     * @param numberTwo the second number.
     * @return the result of the divide operation of two numbers.
     * @throws UndefinedNumberException if second number is zero.
     */
    @Override
    public BigDecimal execute(BigDecimal numberOne, BigDecimal numberTwo) {

        if (numberTwo.compareTo(BigDecimal.ZERO) == 0) {
            throw new UndefinedNumberException("Division by zero !");
        }
        return numberOne.divide(numberTwo, SCALE, BigDecimal.ROUND_HALF_UP);
    }
}
