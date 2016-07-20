package com.malex.exception.impl;

import com.malex.exception.CalculatorAppException;

public class IncorrectDataException extends CalculatorAppException{
    public IncorrectDataException(String message) {
        super(message);
    }
}
