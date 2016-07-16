package com.malex.service.impl;

import com.malex.model.enums.OperationName;
import com.malex.service.AbstractOperation;

public class MultiplicationOperation extends AbstractOperation {

    public MultiplicationOperation() {
        super(OperationName.MULTIPLICATION);
    }

    @Override
    public int execute(int numberOne, int numberTwo) {
        return numberOne * numberTwo;
    }
}
