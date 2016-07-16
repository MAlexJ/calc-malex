package com.malex.service.impl;

import com.malex.model.enums.OperationName;
import com.malex.service.AbstractOperation;

public class SubtractionOperation extends AbstractOperation {

    public SubtractionOperation() {
        super(OperationName.SUBTRACTION);
    }

    @Override
    public int execute(int numberOne, int numberTwo) {
        return numberOne - numberTwo;
    }
}
