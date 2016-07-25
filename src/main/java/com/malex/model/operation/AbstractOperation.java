package com.malex.model.operation;


import com.malex.model.enums.Operation;

public abstract class AbstractOperation implements ArithmeticOperation {
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
    public AbstractOperation(Operation operationName) {
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
