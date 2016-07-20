package com.malex.exception.impl;

import com.malex.exception.CalculatorAppException;

public class UndefinedNumberException extends CalculatorAppException {

    public UndefinedNumberException(String message) {
        super(message);
    }
}
