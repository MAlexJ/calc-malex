package com.malex.service;


import com.malex.model.enums.OperationName;

public abstract class AbstractOperation implements Operation {
    private OperationName operationName;

    public AbstractOperation(OperationName operationName) {
        this.operationName = operationName;
    }

    @Override
    public OperationName getOperationName() {
        return operationName;
    }
}
