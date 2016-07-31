package com.malex.model.operation;

import com.malex.model.enums.Operation;

import java.math.BigDecimal;

/**
 * The {@code PercentOperation} class represents the algorithm for find the percent.
 */
public class PercentOperation extends AbstractOperation {
    /**
     * Value is used to store of the number: ONE_HUNDRED.
     */
    private static final BigDecimal ONE_HUNDRED = new BigDecimal(100);

    /**
     * Initializes a newly created {@code PercentOperation} object.
     * {@code Operation}
     */
    public PercentOperation() {
        super(Operation.PERCENT);
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
        return number.multiply(percentNumber, MATH_CONTEXT).divide(ONE_HUNDRED, SCALE, BigDecimal.ROUND_HALF_UP);
    }

}
