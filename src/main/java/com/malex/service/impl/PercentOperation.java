package com.malex.service.impl;

import com.malex.model.enums.OperationName;
import com.malex.service.AbstractOperation;

import java.math.BigDecimal;

public class PercentOperation extends AbstractOperation {

    private static final BigDecimal ONE_HUNDRED = new BigDecimal(100);

    public PercentOperation() {
        super(OperationName.PERCENT);
    }

    @Override
    public BigDecimal execute(BigDecimal number, BigDecimal percentNumber) {
        return number.multiply(percentNumber).divide(ONE_HUNDRED, 14, BigDecimal.ROUND_HALF_UP);
    }

}
