package com.malex.service;

import com.malex.model.enums.OperationName;

import java.math.BigDecimal;

/**
 * The main interface {@code Operation} of the arithmetic operation.
 */
public interface Operation {

    /**
     * Get the arithmetic operation.
     *
     * @return the arithmetic operation.
     */
    OperationName getOperationName();

    /**
     * Execute predetermined arithmetic operation.
     *
     * @param numberOne the first number.
     * @param numberTwo the second number.
     * @return result of the arithmetic operation.
     */
    BigDecimal execute(BigDecimal numberOne, BigDecimal numberTwo);
}
