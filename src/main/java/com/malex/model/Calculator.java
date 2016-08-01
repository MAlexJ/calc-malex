package com.malex.model;

import com.malex.model.enums.Operation;
import com.malex.model.exception.UndefinedNumberException;
import com.malex.model.operation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * The {@code Calculator} class represents the arithmetic algorithm of two numbers.
 * The class stores arithmetic calculation in memory: {@code AdditionOperation},{@code DivisionOperation},{@code MultiplicationOperation},{@code SubtractionOperation},{@code PercentOperation}.
 * <p>
 * Here are some more examples of how this can be used:
 * <pre>
 * //Added arithmetic operation
 * Calculator.addOperation(new AdditionOperation());
 * Calculator.addOperation(new DivisionOperation());
 * Calculator.addOperation(new SubtractionOperation());
 * Calculator.addOperation(new MultiplicationOperation());
 * Calculator.addOperation(new PercentOperation());
 * </pre>
 *
 * @author MAlex
 * @see com.malex.model.operation.ArithmeticOperation
 * @see com.malex.model.enums.Operation
 */
public class Calculator {

    /**
     * Value is used to store the maximum limit number for calculations.
     */
    private final static BigDecimal MAX_LIMIT_VALUE;


    /**
     * Value is used to store the minimum limit number for calculations.
     */
    private final static BigDecimal MIN_LIMIT_VALUE;

    /**
     * Value is used to store model  {@code Calculator}.
     */
    public final static Calculator CALCULATOR;

    /**
     * Value is used to store scale of the number.
     */
    private static final int SCALE = 14;

    /**
     * Initialization the model of a Calculator.
     */
    static {
        CALCULATOR = new Calculator();
        CALCULATOR.addOperation(new AdditionOperation());
        CALCULATOR.addOperation(new DivisionOperation());
        CALCULATOR.addOperation(new SubtractionOperation());
        CALCULATOR.addOperation(new MultiplicationOperation());
        CALCULATOR.addOperation(new PercentOperation());
        MIN_LIMIT_VALUE = new BigDecimal("-1E150");
        MAX_LIMIT_VALUE = new BigDecimal("1E150");
    }

    /**
     * Value is used to store map {@code Operation} of operations:  ADDITION, SUBTRACTION, DIVISION, MULTIPLICATION, PERCENT.
     */
    private Map<Operation, ArithmeticOperation> operations;

    /**
     * Initializes a newly created {@code Calculator} object.
     */
    private Calculator() {
        this.operations = new HashMap<>();
    }

    /**
     * Add operation the arithmetic operation.
     *
     * @param operation the arithmetic operation.
     */
    private void addOperation(ArithmeticOperation operation) {
        operations.put(operation.getOperationName(), operation);
    }

    /**
     * Calculate result of the arithmetic operation two numbers.
     *
     * @param operationName the arithmetic operator:  ADDITION, SUBTRACTION, DIVISION, MULTIPLICATION, PERCENT.
     * @param numberOne     the first number.
     * @param numberTwo     the second number.
     * @return result of the arithmetic operation.
     * @throws UndefinedNumberException if division by zero.
     */
    public BigDecimal calculate(Operation operationName, BigDecimal numberOne, BigDecimal numberTwo) throws UndefinedNumberException {
        validateInputParameters(operationName, numberOne, numberTwo);

        ArithmeticOperation operation = operations.get(operationName);
        BigDecimal result = operation.execute(numberOne, numberTwo);
        validateResultPermissLimitCalculations(result);

        return result.setScale(SCALE, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * Validate input parameters.
     *
     * @param operationName the arithmetic operator.
     * @param numberOne     the first number.
     * @param numberTwo     the second number.
     * @throws IllegalArgumentException if incorrect arithmetic operation.
     * @throws IllegalArgumentException if incorrect the first number or the second number.
     */
    private void validateInputParameters(Operation operationName, BigDecimal numberOne, BigDecimal numberTwo) {
        if (operationName == null) {
            throw new IllegalArgumentException("Incorrect arithmetic operation !");
        }
        if (numberOne == null || numberTwo == null) {
            throw new IllegalArgumentException("One of the numbers is equal \'null\' !");
        }
    }

    /**
     * Validate the result exceeding the permissible limit calculations.
     *
     * @param result the calculation result.
     */
    private void validateResultPermissLimitCalculations(BigDecimal result) throws UndefinedNumberException {
        if (result.compareTo(MAX_LIMIT_VALUE) >= 0 || result.compareTo(MIN_LIMIT_VALUE) <= 0) {
            throw new UndefinedNumberException("Exceeded the allowable limit: 1E150");
        }
    }
}
