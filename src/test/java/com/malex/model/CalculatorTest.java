package com.malex.model;

import com.malex.model.exception.UndefinedNumberException;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;

import static com.malex.model.Calculator.CALCULATOR;
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

        test("MULTIPLICATION", bigNumMAX.toString(), bigNumMAX.toString(), "4.611686014132421E18");
        test("MULTIPLICATION", bigNumMAX.toString(), bigNumMIN.toString(), "-4.611686016279904E18");

        test("SUBTRACTION", bigNumMAX.toString(), bigNumMIN.toString(), bigNumMAX.subtract(bigNumMIN).toString());
        test("SUBTRACTION", bigNumMIN.toString(), bigNumMIN.toString(), bigNumMIN.subtract(bigNumMIN).toString());

        BigDecimal bigDecimalMAX = new BigDecimal(String.valueOf(Double.MAX_VALUE));
        BigDecimal bigDecimalMIN = new BigDecimal(String.valueOf(Double.MIN_VALUE));

        test("ADD", bigDecimalMAX.toPlainString(), bigDecimalMIN.toPlainString(), "1.797693134862316E308");
        test("SUBTRACTION", bigDecimalMAX.toPlainString(), bigDecimalMIN.toPlainString(), "1.797693134862316E308");
        test("MULTIPLICATION", bigDecimalMAX.toPlainString(), bigDecimalMIN.toPlainString(), "0");
        test("DIVISION", bigDecimalMAX.toPlainString(), bigDecimalMIN.toPlainString(), bigDecimalMAX.divide(bigDecimalMIN, 14, BigDecimal.ROUND_HALF_UP).toPlainString());
    }

    @Test
    public void testTransitionToEngineeringView() {
        test("ADD", "9999999999999999", "1", "1E16"); // Limit 1E16
        test("ADD", "1E18", "66666666", "1.000000000066667E18");
        test("ADD", "00.0000000000", "0E-10", "0");
        test("ADD", "13.300000000000000710542735760100185871124267578125", "0E-10", "13.3");

        test("SUBTRACTION", "-9999999999999999", "1", "-1E16"); // Limit -1E16
        test("SUBTRACTION", "99999", "1E18", "-9.999999999999E17");
        test("SUBTRACTION", "13.300000000000000710542735760100185871124267578125", "0.1", "13.2");

        test("DIVISION", "1", "99999999999999", "0.00000000000001"); // Limit 0.00000000000001
        test("DIVISION", "13.300000000000000710542735760100185871124267578125", "1", "13.3");
        test("DIVISION", "13.300000000000000710542735760100185871124267578125", "1", "13.3");
        test("DIVISION", "26.00000000000000710542735760100185871124267578125", "4", "6.5");

        test("MULTIPLICATION", "13.300000000000000710542735760100185871124267578125", "1", "13.3");
        test("MULTIPLICATION", "0.000000000000004855004846845542", "1", "0");
        test("MULTIPLICATION", "0.000000000000044550048462", "1", "0.00000000000004");
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
        test("MULTIPLICATION", "111111111", "111111111", "1.234567898765432E16");
        test("MULTIPLICATION", "12345679", "9", "111111111");
    }

    private void incorrectValues(String operationName, String numberOne, String numberTwo) {
        try {
            //when
            CALCULATOR.calculate(operationName, numberOne, numberTwo);
            fail("Arithmetic expression: numberOne: " + numberOne + ", operation: " + operationName + ", numberTwo: " + numberTwo + " is valid!");
        } catch (UndefinedNumberException | IllegalArgumentException e) {
            //Ignore
        }
    }

    private void test(String operationName, String numberOne, String numberTwo, String expectedResult) {
        //when
        String actualResult = null;
        try {
            actualResult = CALCULATOR.calculate(operationName, numberOne, numberTwo);
        } catch (UndefinedNumberException e) {
            fail("Fail test param: " + operationName + " " + numberOne + " " + numberTwo);
        }

        //then
        assertEquals(expectedResult, actualResult);
    }
}
