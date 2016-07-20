package com.malex.exception.impl;

import com.malex.exception.CalculatorAppException;

public class NoSuchOperationException extends CalculatorAppException {

    public NoSuchOperationException(String message) {
        super(message);
    }
}
