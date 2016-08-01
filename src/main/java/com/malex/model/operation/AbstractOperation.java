package com.malex.model.operation;

import com.malex.model.enums.Operation;

import java.math.MathContext;
import java.math.RoundingMode;

abstract class AbstractOperation implements ArithmeticOperation {
    /**
     * Value is used to the precision of arithmetic.
     */
    protected static final MathContext MATH_CONTEXT;

    /**
     * Value is used to store of the number of digits after the decimal point.
     */
    private static final int PRECISION = 16;

    /**
     * Scale of the number.
     */
    protected static final int SCALE = 14;

    /**
     * Initialization.
     */
    static {
        MATH_CONTEXT = new MathContext(PRECISION, RoundingMode.HALF_UP);
    }

    /**
     * The name of arithmetic operation.
     */
    private Operation operationName;

    /**
     * Initializes a newly created {@code AbstractOperation} object.
     * Set a specific arithmetic operation: ADDITION, SUBTRACTION, DIVISION, MULTIPLICATION, PERCENT.
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
