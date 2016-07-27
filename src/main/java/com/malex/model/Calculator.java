package com.malex.model;

import com.malex.model.enums.Operation;
import com.malex.model.exception.NoSuchOperationException;
import com.malex.model.exception.UndefinedNumberException;
import com.malex.model.operation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The {@code Calculator} class represents the arithmetic algorithm of two numbers.
 * The class stores arithmetic calculation in memory: {@code AddOperation},{@code DivisionOperation},{@code MultiplicationOperation},{@code SubtractionOperation},{@code PercentOperation}.
 * <p>
 * Here are some more examples of how this can be used:
 * <pre>
 * //Added arithmetic operation
 * Calculator.addOperation(new AddOperation());
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
     * Value is used to store model  {@code Calculator}.
     */
    public final static Calculator CALCULATOR;

    /**
     * Initialization the model of a Calculator.
     */
    static {
        CALCULATOR = new Calculator();
        CALCULATOR.addOperation(new AddOperation());
        CALCULATOR.addOperation(new DivisionOperation());
        CALCULATOR.addOperation(new SubtractionOperation());
        CALCULATOR.addOperation(new MultiplicationOperation());
        CALCULATOR.addOperation(new PercentOperation());
    }

    /**
     * Value is used to store map {@code Operation} of operations:  ADD, SUBTRACTION, DIVISION, MULTIPLICATION, PERCENT.
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
     * @param operationName the arithmetic operator:  ADD, SUBTRACTION, DIVISION, MULTIPLICATION, PERCENT.
     * @param numberOne     the first number.
     * @param numberTwo     the second number.
     * @return result of the arithmetic operation.
     * @throws UndefinedNumberException if division by zero.
     */
    public String calculate(String operationName, String numberOne, String numberTwo) throws UndefinedNumberException {
        validateInputParameters(operationName, numberOne, numberTwo);

        Operation name = Operation.get(operationName);

        ArithmeticOperation operation = operations.get(name);
        BigDecimal result = operation.execute(new BigDecimal(numberOne), new BigDecimal(numberTwo));
        return result.stripTrailingZeros().toPlainString();
    }

    /**
     * Validate input parameters.
     *
     * @param operationName the arithmetic operator.
     * @param numberOne     the first number.
     * @param numberTwo     the second number.
     * @throws NoSuchOperationException if incorrect arithmetic operation.
     * @throws IllegalArgumentException if incorrect the first number or the second number.
     */
    private void validateInputParameters(String operationName, String numberOne, String numberTwo) {
        if (operationName == null || operationName.equals("")) {
            throw new NoSuchOperationException("Incorrect arithmetic operation !");
        }

        if (numberOne == null || numberTwo == null) {
            throw new IllegalArgumentException("One of the numbers is equal \'null\' !");
        }

        if (numberOne.equals("") || numberTwo.equals("")) {
            throw new IllegalArgumentException("One of the numbers is empty !!");
        }
        if (!verificationNumber(numberOne)) {
            throw new IllegalArgumentException("Value \'" + numberOne + "\' is not a number !");
        }
        if (!verificationNumber(numberTwo)) {
            throw new IllegalArgumentException("Value \'" + numberTwo + "\' is not a number !");
        }
    }

    /**
     * Verification of the number.
     *
     * @param number incoming the value.
     * @return true if the input parameter is the number.
     */
    private boolean verificationNumber(String number) {
        Pattern pattern = Pattern.compile("[0-9|.|-]+");
        Matcher matcher = pattern.matcher(number);
        return matcher.matches();
    }
}
