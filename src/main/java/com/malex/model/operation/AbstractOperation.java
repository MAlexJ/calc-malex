package com.malex.model.operation;


import com.malex.model.enums.Operation;

import java.math.MathContext;
import java.math.RoundingMode;

abstract class AbstractOperation implements ArithmeticOperation {
    /**
     * Value is used to store of the unlimited precision arithmetic.
     */
    static final MathContext MATH_CONTEXT;

    /**
     * Value is used to store of the specified precision.
     */
    private static final int PRECISION = 16;

    static {
        MATH_CONTEXT = new MathContext(PRECISION, RoundingMode.HALF_UP);
    }

    /**
     * Value is used to store of the name of arithmetic operation.
     */
    private Operation operationName;

    /**
     * Initializes a newly created {@code AbstractOperation} object.
     * Set a specific arithmetic operation: ADD, SUBTRACTION, DIVISION, MULTIPLICATION, PERCENT.
     *
     * @param operationName the arithmetic operation.
     */
    AbstractOperation(Operation operationName) {
        this.operationName = operationName;
    }

    /**
     * Get the arithmetic operation.
     *
     * @return the arithmetic operation.
     */
    @Override
    public Operation getOperationName() {
        return operationName;
    }
}
