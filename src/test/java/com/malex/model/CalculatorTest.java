package com.malex.model;

import com.malex.model.exception.NoSuchOperationException;
import com.malex.model.exception.UndefinedNumberException;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;

import static com.malex.model.Calculator.calculator;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;

public class CalculatorTest {

    /**
     * Arithmetic operations:
     * ADD
     * SUBTRACTION
     * DIVISION
     * MULTIPLICATION
     * PERCENT
     */
    @Test
    public void testSimplyNumbers() {
        test("ADD", "1", "2", "3");
        test("ADD", "0.00", "1", "1");
        test("ADD", "0.00", "0", "0");
        test("ADD", "0.0001", "1", "1.0001");

        test("SUBTRACTION", "0", "1", "-1");
        test("SUBTRACTION", "0.00", "1", "-1");

        test("SUBTRACTION", "1", "2", "-1");
        test("DIVISION", "1", "2", "0.5");
        test("DIVISION", "0", "1", "0");

        test("MULTIPLICATION", "1", "2", "2");

        BigInteger bigNumMAX = new BigInteger(String.valueOf(Integer.MAX_VALUE));
        BigInteger bigNumMIN = new BigInteger(String.valueOf(Integer.MIN_VALUE));

        test("ADD", bigNumMAX.toString(), bigNumMAX.toString(), bigNumMAX.add(bigNumMAX).toString());
        test("ADD", bigNumMAX.toString(), bigNumMIN.toString(), "-1");
        test("ADD", bigNumMIN.toString(), bigNumMAX.toString(), "-1");

        test("MULTIPLICATION", bigNumMAX.toString(), bigNumMAX.toString(), bigNumMAX.multiply(bigNumMAX).toString());
        test("MULTIPLICATION", bigNumMAX.toString(), bigNumMIN.toString(), bigNumMAX.multiply(bigNumMIN).toString());

        test("SUBTRACTION", bigNumMAX.toString(), bigNumMIN.toString(), bigNumMAX.subtract(bigNumMIN).toString());
        test("SUBTRACTION", bigNumMIN.toString(), bigNumMIN.toString(), bigNumMIN.subtract(bigNumMIN).toString());

        BigDecimal bigDecimalMAX = new BigDecimal(String.valueOf(Double.MAX_VALUE));
        BigDecimal bigDecimalMIN = new BigDecimal(String.valueOf(Double.MIN_VALUE));

        test("ADD", bigDecimalMAX.toPlainString(), bigDecimalMIN.toPlainString(), bigDecimalMAX.add(bigDecimalMIN).toPlainString());
        test("SUBTRACTION", bigDecimalMAX.toPlainString(), bigDecimalMIN.toPlainString(), bigDecimalMAX.subtract(bigDecimalMIN).toPlainString());
        test("MULTIPLICATION", bigDecimalMAX.toPlainString(), bigDecimalMIN.toPlainString(), bigDecimalMAX.multiply(bigDecimalMIN).toPlainString());
        test("DIVISION", bigDecimalMAX.toPlainString(), bigDecimalMIN.toPlainString(), bigDecimalMAX.divide(bigDecimalMIN, 14, BigDecimal.ROUND_HALF_UP).toPlainString());
    }

    @Test
    public void testPercent() {
        test("PERCENT", "0", "2", "0");
        test("PERCENT", "1", "2", "0.02");
        test("PERCENT", "1.0", "2", "0.02");
        test("PERCENT", "55", "20.00", "11");
        test("PERCENT", "0.1", "2", "0.002");

        test("PERCENT", "2", "50", "1");
        test("PERCENT", "2", "90", "1.8");
        test("PERCENT", "11111111", "50", "5555555.5");
        test("PERCENT", "-2", "50", "-1");
        test("PERCENT", "2", "-50", "-1");
    }

    @Test
    public void testIncorrectInputParameters() {
        // null
        incorrectValues(null, "1", "2");
        incorrectValues("ADD", null, "2");
        incorrectValues("ADD", "3", null);
        incorrectValues("ADD", null, null);
        incorrectValues(null, "-43", null);

        // empty
        incorrectValues("", "", "");
        incorrectValues("", "1", "2");
        incorrectValues("ADD", "", "2");
        incorrectValues("MULTIPLICATION", "1", "");

        // not number
        incorrectValues("ccccc", "aaaa", "bbbbb");
        incorrectValues("xxxxxxx", "1", "2");
        incorrectValues("ADD", "number", "2");
        incorrectValues("MULTIPLICATION", "1", "number");

        // division by zero
        incorrectValues("DIVISION", "1", "0");
        incorrectValues("DIVISION", "1.00", "0");
        incorrectValues("DIVISION", "0.001", "0");
    }

    @Test
    public void testAccuracy() {
        test("MULTIPLICATION", "111111111", "111111111", "12345678987654321");
        test("MULTIPLICATION", "12345679", "9", "111111111");
    }

    private void incorrectValues(String operationName, String numberOne, String numberTwo) {
        try {
            //when
            calculator.calculate(operationName, numberOne, numberTwo);
            fail("Arithmetic expression: numberOne: " + numberOne + ", operation: " + operationName + ", numberTwo: " + numberTwo + " is valid!");
        } catch (UndefinedNumberException | NoSuchOperationException | IllegalArgumentException e) {
            //Ignore
        }
    }

    private void test(String operationName, String numberOne, String numberTwo, String expectedResult) {
        //when
        String actualResult = null;
        try {
            actualResult = calculator.calculate(operationName, numberOne, numberTwo);
        } catch (UndefinedNumberException e) {
            fail("Fail test param: " + operationName + " " + numberOne + " " + numberTwo);
        }

        //then
        assertEquals(expectedResult, actualResult);
    }
}
