package com.malex.exception;

public class CalculatorAppException extends RuntimeException {
    public CalculatorAppException() {
    }

    public CalculatorAppException(String message) {
        super(message);
    }

    public CalculatorAppException(String message, Throwable cause) {
        super(message, cause);
    }
}
