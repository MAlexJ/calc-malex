package com.malex.service.impl;

import com.malex.model.enums.OperationName;
import com.malex.service.AbstractOperation;

import java.math.BigDecimal;

public class AddOperation extends AbstractOperation {

    public AddOperation() {
        super(OperationName.ADD);
    }

    @Override
    public BigDecimal execute(BigDecimal numberOne, BigDecimal numberTwo) {
        return numberOne.add(numberTwo);
    }
}
