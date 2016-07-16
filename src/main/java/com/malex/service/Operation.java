package com.malex.service;

import com.malex.model.enums.OperationName;

public interface Operation {

    OperationName getOperationName();

    int execute(int numberOne, int numberTwo);
}
