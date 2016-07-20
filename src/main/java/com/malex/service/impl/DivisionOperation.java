package com.malex.service.impl;

import com.malex.exception.impl.UndefinedNumberException;
import com.malex.model.enums.OperationName;
import com.malex.service.AbstractOperation;

import java.math.BigDecimal;

public class DivisionOperation extends AbstractOperation {

    public DivisionOperation() {
        super(OperationName.DIVISION);
    }

    @Override
    public BigDecimal execute(BigDecimal numberOne, BigDecimal numberTwo) {

        if (numberTwo.compareTo(BigDecimal.ZERO) == 0) {
            throw new UndefinedNumberException("Division by zero !");
        }
        return numberOne.divide(numberTwo, 14, BigDecimal.ROUND_HALF_UP);
    }
}
