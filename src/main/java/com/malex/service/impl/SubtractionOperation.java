package com.malex.service.impl;

import com.malex.model.enums.OperationName;
import com.malex.service.AbstractOperation;

import java.math.BigDecimal;

public class SubtractionOperation extends AbstractOperation {

    public SubtractionOperation() {
        super(OperationName.SUBTRACTION);
    }

    @Override
    public BigDecimal execute(BigDecimal numberOne, BigDecimal numberTwo) {
        return numberOne.subtract(numberTwo);
    }
}
