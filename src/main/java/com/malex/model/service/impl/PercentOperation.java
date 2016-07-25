package com.malex.model.service.impl;

import com.malex.model.enums.OperationName;
import com.malex.model.service.AbstractOperation;

import java.math.BigDecimal;

public class PercentOperation extends AbstractOperation {
    /**
     * Value is used to store of the number: ONE_HUNDRED.
     */
    private static final BigDecimal ONE_HUNDRED = new BigDecimal(100);

    /**
     * Value is used to store scale of the number.
     */
    private static final int SCALE = 14;

    /**
     * Initializes a newly created {@code PercentOperation} object.
     * {@code OperationName}
     */
    public PercentOperation() {
        super(OperationName.PERCENT);
    }

    /**
     * Execute the percentage operation.
     * Round number: {@code BigDecimal.ROUND_HALF_UP}
     *
     * @param number        the number.
     * @param percentNumber the percent number.
     * @return the result of the percentage.
     */
    @Override
    public BigDecimal execute(BigDecimal number, BigDecimal percentNumber) {
        return number.multiply(percentNumber).divide(ONE_HUNDRED, SCALE, BigDecimal.ROUND_HALF_UP);
    }

}
