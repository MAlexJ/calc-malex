package com.malex.model;

import com.malex.exception.impl.NoSuchOperationException;
import com.malex.exception.impl.UndefinedNumberException;
import com.malex.service.impl.*;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;

public class CalculatorTest {

    private static Calculator calculator;

    @BeforeClass
    public static void init() {
        calculator = new Calculator();
        calculator.addOperation(new AddOperation());
        calculator.addOperation(new DivisionOperation());
        calculator.addOperation(new MultiplicationOperation());
        calculator.addOperation(new SubtractionOperation());
        calculator.addOperation(new PercentOperation());
    }

    /**
     * Оперции доступные по id:
     * add
     * subtraction
     * division
     * multiplication
     * percent
     */
    @Test
    public void testSimplyNumbers() {
        test("add", "1", "2", "3");
        test("add", "0.00", "1", "1");
        test("add", "0.00", "0", "0");
        test("add", "0.0001", "1", "1.0001");

        test("subtraction", "0", "1", "-1");
        test("subtraction", "0.00", "1", "-1");

        test("subtraction", "1", "2", "-1");
        test("division", "1", "2", "0.5");
        test("division", "0", "1", "0");

        test("multiplication", "1", "2", "2");

        BigInteger bigNumMAX = new BigInteger(String.valueOf(Integer.MAX_VALUE));
        BigInteger bigNumMIN = new BigInteger(String.valueOf(Integer.MIN_VALUE));

        test("add", bigNumMAX.toString(), bigNumMAX.toString(), bigNumMAX.add(bigNumMAX).toString());
        test("add", bigNumMAX.toString(), bigNumMIN.toString(), "-1");
        test("add", bigNumMIN.toString(), bigNumMAX.toString(), "-1");

        test("multiplication", bigNumMAX.toString(), bigNumMAX.toString(), bigNumMAX.multiply(bigNumMAX).toString());
        test("multiplication", bigNumMAX.toString(), bigNumMIN.toString(), bigNumMAX.multiply(bigNumMIN).toString());

        test("subtraction", bigNumMAX.toString(), bigNumMIN.toString(), bigNumMAX.subtract(bigNumMIN).toString());
        test("subtraction", bigNumMIN.toString(), bigNumMIN.toString(), bigNumMIN.subtract(bigNumMIN).toString());

        BigDecimal bigDecimalMAX = new BigDecimal(String.valueOf(Double.MAX_VALUE));
        BigDecimal bigDecimalMIN = new BigDecimal(String.valueOf(Double.MIN_VALUE));

        test("add", bigDecimalMAX.toString(), bigDecimalMIN.toString(), bigDecimalMAX.add(bigDecimalMIN).toString());
        test("subtraction", bigDecimalMAX.toString(), bigDecimalMIN.toString(), bigDecimalMAX.subtract(bigDecimalMIN).toString());
        test("multiplication", bigDecimalMAX.toString(), bigDecimalMIN.toString(), bigDecimalMAX.multiply(bigDecimalMIN).toPlainString());
        test("division", bigDecimalMAX.toString(), bigDecimalMIN.toString(), bigDecimalMAX.divide(bigDecimalMIN, 14, BigDecimal.ROUND_HALF_UP).toPlainString());
    }

    @Test
    public void testPercent() {
        test("percent", "0", "2", "0");
        test("percent", "1", "2", "0.02");
        test("percent", "1.0", "2", "0.02");
        test("percent", "55", "20.00", "11");
        test("percent", "0.1", "2", "0.002");

        test("percent", "2", "50", "1");
        test("percent", "2", "90", "1.8");
        test("percent", "11111111", "50", "5555555.5");
        test("percent", "-2", "50", "-1");
        test("percent", "2", "-50", "-1");
    }

    @Test
    public void testIncorrectValues() {
        incorrectValues(null, null, null);
        incorrectValues(null, "1", "2");
        incorrectValues("+", null, "2");
        incorrectValues("+", "3", null);

        incorrectValues("", "", "");
        incorrectValues("", "1", "2");
        incorrectValues("+", "", "2");
        incorrectValues("-", "1", "");

        incorrectValues("ccccc", "aaaa", "bbbbb");
        incorrectValues("xxxxxxx", "1", "2");
        incorrectValues("+", "number", "2");
        incorrectValues("-", "1", "number");

        incorrectValues("division", "1", "0");
        incorrectValues("division", "1.00", "0");
        incorrectValues("division", "0.001", "0");
    }

    @Test
    public void testAccuracy() {
        test("multiplication", "111111111", "111111111", "12345678987654321");
        test("multiplication", "12345679", "9", "111111111");
    }

    private void incorrectValues(String operationName, String numberOne, String numberTwo) {
        try {
            //when
            calculator.calculate(operationName, numberOne, numberTwo);
            fail("Arithmetic expression: numberOne: " + numberOne + ", operation: " + operationName + ", numberTwo: " + numberTwo + " is valid!");
        } catch (UndefinedNumberException | NoSuchOperationException e) {
            //Ignore
        }
    }

    private void test(String operationName, String numberOne, String numberTwo, String expectedResult) {
        //when
        String actualResult = calculator.calculate(operationName, numberOne, numberTwo);

        //then
        assertEquals(expectedResult, actualResult);
    }
}
