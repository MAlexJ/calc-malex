package com.malex.service;


import com.malex.model.enums.OperationName;

public abstract class AbstractOperation implements Operation {
    /**
     * Value is used to store of the name of arithmetic operation.
     */
    private OperationName operationName;

    /**
     * Initializes a newly created {@code AbstractOperation} object.
     * Set a specific arithmetic operation: ADD, SUBTRACTION, DIVISION, MULTIPLICATION, PERCENT.
     *
     * @param operationName the arithmetic operation.
     */
    public AbstractOperation(OperationName operationName) {
        this.operationName = operationName;
    }

    /**
     * Get the arithmetic operation.
     *
     * @return the arithmetic operation.
     */
    @Override
    public OperationName getOperationName() {
        return operationName;
    }
}
