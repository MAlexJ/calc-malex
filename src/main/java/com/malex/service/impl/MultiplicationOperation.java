package com.malex.service.impl;

import com.malex.model.enums.OperationName;
import com.malex.service.AbstractOperation;

import java.math.BigDecimal;

public class MultiplicationOperation extends AbstractOperation {

    public MultiplicationOperation() {
        super(OperationName.MULTIPLICATION);
    }

    @Override
    public BigDecimal execute(BigDecimal numberOne, BigDecimal numberTwo) {
        return numberOne.multiply(numberTwo);
    }
}
