package com.malex.service.impl;

import com.malex.model.enums.OperationName;
import com.malex.service.AbstractOperation;

public class DivisionOperation extends AbstractOperation {

    public DivisionOperation() {
        super(OperationName.DIVISION);
    }

    @Override
    public int execute(int numberOne, int numberTwo) {

        if (numberTwo == 0) {
            return 0;
        }
        return numberOne / numberTwo;
    }
}
