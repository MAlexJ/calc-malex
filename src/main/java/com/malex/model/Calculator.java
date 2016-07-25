package com.malex.model;

import com.malex.model.exception.NoSuchOperationException;
import com.malex.model.exception.UndefinedNumberException;
import com.malex.model.enums.OperationName;
import com.malex.model.service.Operation;

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
 * Calculator calculator = new Calculator();
 * //Added arithmetic operation
 * calculator.addOperation(new AddOperation());
 * calculator.addOperation(new DivisionOperation());
 * calculator.addOperation(new SubtractionOperation());
 * calculator.addOperation(new MultiplicationOperation());
 * calculator.addOperation(new PercentOperation());
 * </pre>
 *
 * @author MAlex
 * @see java.lang.String
 * @see java.util.Map;
 * @see java.util.HashMap;
 * @see java.util.regex.Matcher;
 * @see java.util.regex.Pattern;
 * @see java.math.BigDecimal;
 * @see com.malex.model.service.Operation;
 * @see com.malex.model.enums.OperationName;
 */
public class Calculator {

    /**
     * Value is used to store map {@code Operation} of operations:  ADD, SUBTRACTION, DIVISION, MULTIPLICATION, PERCENT.
     */
    private Map<OperationName, Operation> operations;

    /**
     * Initializes a newly created {@code Calculator} object.
     */
    public Calculator() {
        this.operations = new HashMap<>();
    }

    /**
     * Add operation the arithmetic operation.
     *
     * @param operation the arithmetic operation.
     */
    public void addOperation(Operation operation) {
        operations.put(operation.getOperationName(), operation);
    }

    /**
     * Calculate result of the arithmetic operation two numbers.
     *
     * @param operationName the arithmetic operator:  ADD, SUBTRACTION, DIVISION, MULTIPLICATION, PERCENT.
     * @param numberOne     the first number.
     * @param numberTwo     the second number.
     * @return result of the arithmetic operation.
     */
    public String calculate(String operationName, String numberOne, String numberTwo) {
        validateInputParameters(operationName, numberOne, numberTwo);

        OperationName name = OperationName.get(operationName);
        Operation operation = operations.get(name);
        BigDecimal result = operation.execute(new BigDecimal(numberOne), new BigDecimal(numberTwo));
        return result.stripTrailingZeros().toPlainString();
    }

    /**
     * Validate input parameters.
     *
     * @param operationName the arithmetic operator.
     * @param numberOne     the first number.
     * @param numberTwo     the second number.
     */
    private void validateInputParameters(String operationName, String numberOne, String numberTwo) {
        if (operationName == null || operationName.equals("")) {
            throw new NoSuchOperationException("Incorrect arithmetic operation !");
        }

        if (numberOne == null || numberTwo == null) {
            throw new UndefinedNumberException("One of the numbers is equal \'null\' !");
        }

        if (numberOne.equals("") || numberTwo.equals("")) {
            throw new UndefinedNumberException("One of the numbers is empty !!");
        }
        if (!verificationNumber(numberOne)) {
            throw new UndefinedNumberException("Value \'" + numberOne + "\' is not a number !");
        }
        if (!verificationNumber(numberTwo)) {
            throw new UndefinedNumberException("Value \'" + numberTwo + "\' is not a number !");
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
