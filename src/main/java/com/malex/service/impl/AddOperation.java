package com.malex.service.impl;

import com.malex.model.enums.OperationName;
import com.malex.service.AbstractOperation;

public class AddOperation extends AbstractOperation {

    public AddOperation() {
        super(OperationName.ADD);
    }

    @Override
    public int execute(int numberOne, int numberTwo) {
        return numberOne + numberTwo;
    }
}
