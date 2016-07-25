package com.malex.model.operation;

import com.malex.model.enums.Operation;

import java.math.BigDecimal;

/**
 * The main interface {@code Operation} of the arithmetic operation.
 */
public interface ArithmeticOperation {

    /**
     * Get the arithmetic operation.
     *
     * @return the arithmetic operation.
     */
    Operation getOperationName();

    /**
     * Execute predetermined arithmetic operation.
     *
     * @param numberOne the first number.
     * @param numberTwo the second number.
     * @return result of the arithmetic operation.
     */
    BigDecimal execute(BigDecimal numberOne, BigDecimal numberTwo);
}
