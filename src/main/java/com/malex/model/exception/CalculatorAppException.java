package com.malex.model.exception;

public abstract class CalculatorAppException extends RuntimeException {
    public CalculatorAppException() {
    }

    public CalculatorAppException(String message) {
        super(message);
    }

    public CalculatorAppException(String message, Throwable cause) {
        super(message, cause);
    }
}
