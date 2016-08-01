package com.malex.model;

import com.malex.model.enums.Operation;
import com.malex.model.exception.UndefinedNumberException;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;

import static com.malex.model.Calculator.CALCULATOR;
import static com.malex.model.enums.Operation.*;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;

public class CalculatorTest {

    private static BigDecimal maxValue;
    private static BigDecimal minValue;

    private static BigDecimal bigDecimalMAX;
    private static BigDecimal bigDecimalMIN;

    private BigDecimal firstNumber;
    private BigDecimal secondNumber;

    @BeforeClass
    public static void init() {
        bigDecimalMAX = new BigDecimal(String.valueOf(Double.MAX_VALUE));
        bigDecimalMIN = new BigDecimal(String.valueOf(Double.MIN_VALUE));
        maxValue = new BigDecimal("9999999999999999");
        minValue = new BigDecimal("-999999999999999");
    }

    /**
     * Arithmetic operations:
     * ADDITION
     * SUBTRACTION
     * DIVISION
     * MULTIPLICATION
     * PERCENT
     */
    @Test
    public void testSimplyNumbers() {
        test(ADDITION, "1", "2", "3");
        test(ADDITION, "0.00", "1", "1");
        test(ADDITION, "0.00", "0", "0");
        test(ADDITION, "0.0001", "1", "1.0001");

        test(SUBTRACTION, "0", "1", "-1");
        test(SUBTRACTION, "0.00", "1", "-1");

        test(SUBTRACTION, "1", "2", "-1");
        test(DIVISION, "1", "2", "0.5");
        test(DIVISION, "0", "1", "0");

        test(MULTIPLICATION, "1", "2", "2");

        BigInteger bigNumMAX = new BigInteger(String.valueOf(Integer.MAX_VALUE));
        BigInteger bigNumMIN = new BigInteger(String.valueOf(Integer.MIN_VALUE));

        test(ADDITION, bigNumMAX.toString(), bigNumMAX.toString(), bigNumMAX.add(bigNumMAX).toString());
        test(ADDITION, bigNumMAX.toString(), bigNumMIN.toString(), "-1");
        test(ADDITION, bigNumMIN.toString(), bigNumMAX.toString(), "-1");

        test(MULTIPLICATION, bigNumMAX.toString(), bigNumMAX.toString(), "4.611686014132421E18");
        test(MULTIPLICATION, bigNumMAX.toString(), bigNumMIN.toString(), "-4.611686016279904E18");

        test(SUBTRACTION, bigNumMAX.toString(), bigNumMIN.toString(), bigNumMAX.subtract(bigNumMIN).toString());
        test(SUBTRACTION, bigNumMIN.toString(), bigNumMIN.toString(), bigNumMIN.subtract(bigNumMIN).toString());

        test(MULTIPLICATION, bigDecimalMAX.toPlainString(), bigDecimalMIN.toPlainString(), "0");
    }

    @Test
    public void testTransitionToEngineeringView() {
        test(ADDITION, "9999999999999999", "1", "1E16"); // Limit 1E16
        test(ADDITION, "1E18", "66666666", "1.000000000066667E18");
        test(ADDITION, "00.0000000000", "0E-10", "0");
        test(ADDITION, "13.300000000000000710542735760100185871124267578125", "0E-10", "13.3");

        test(SUBTRACTION, "-9999999999999999", "1", "-1E16"); // Limit -1E16
        test(SUBTRACTION, "99999", "1E18", "-9.999999999999E17");
        test(SUBTRACTION, "13.300000000000000710542735760100185871124267578125", "0.1", "13.2");

        test(DIVISION, "1", "99999999999999", "0.00000000000001"); // Limit 0.00000000000001
        test(DIVISION, "13.300000000000000710542735760100185871124267578125", "1", "13.3");
        test(DIVISION, "13.300000000000000710542735760100185871124267578125", "1", "13.3");
        test(DIVISION, "26.00000000000000710542735760100185871124267578125", "4", "6.5");

        test(MULTIPLICATION, "13.300000000000000710542735760100185871124267578125", "1", "13.3");
        test(MULTIPLICATION, "0.000000000000004855004846845542", "1", "0");
        test(MULTIPLICATION, "0.000000000000044550048462", "1", "0.00000000000004");

        test(ADDITION, "1E149", "1", "1E149");
        test(SUBTRACTION, "1E149", "1", "1E149");
        test(MULTIPLICATION, "1E149", "1", "1E149");
        test(DIVISION, "1E149", "1", "1E149");

        test(ADDITION, "-1E149", "1", "-1E149");
        test(SUBTRACTION, "-1E149", "1", "-1E149");
        test(MULTIPLICATION, "-1E149", "1", "-1E149");
        test(DIVISION, "-1E149", "1", "-1E149");

        test(ADDITION, "9E149", "1", "9E149");
        test(SUBTRACTION, "-9E149", "1", "-9E149");

        test(SUBTRACTION, "1E150", "1E150", "0");
        test(ADDITION, "1E150", "-1E150", "0");

        test(DIVISION, "0", "1E150", "0");
        test(DIVISION, "0", "-1E150", "0");
        test(MULTIPLICATION, "0", "1E150", "0");
        test(MULTIPLICATION, "0", "-1E150", "0");
    }

    @Test
    public void testPercent() {
        test(PERCENT, "0", "2", "0");
        test(PERCENT, "1", "2", "0.02");
        test(PERCENT, "1.0", "2", "0.02");
        test(PERCENT, "55", "20.00", "11");
        test(PERCENT, "0.1", "2", "0.002");

        test(PERCENT, "2", "50", "1");
        test(PERCENT, "2", "90", "1.8");
        test(PERCENT, "11111111", "50", "5555555.5");
        test(PERCENT, "-2", "50", "-1");
        test(PERCENT, "2", "-50", "-1");
    }

    @Test
    public void testIncorrectInputPermissLimitCalculations() {
        incorrectValues(ADDITION, bigDecimalMAX.toPlainString(), bigDecimalMIN.toPlainString());
        incorrectValues(SUBTRACTION, bigDecimalMAX.toPlainString(), bigDecimalMIN.toPlainString());
        incorrectValues(DIVISION, bigDecimalMAX.toPlainString(), bigDecimalMIN.toPlainString());

        incorrectValues(ADDITION, "1E150", "1");
        incorrectValues(ADDITION, "1E150", "-1E300");

        incorrectValues(SUBTRACTION, "1E151", "1E1");
        incorrectValues(SUBTRACTION, "1E300", "1E150");

        incorrectValues(MULTIPLICATION, "1E300", "1E150");
        incorrectValues(DIVISION, "1E1000", "1E150");

        incorrectValues(DIVISION, "1E149", "0");
        incorrectValues(DIVISION, "-1E149", "0");
    }

    @Test
    public void testIncorrectInputParameters() {
        // null
        incorrectValues(null, "1", "2");

        // division by zero
        incorrectValues(DIVISION, "1", "0");
        incorrectValues(DIVISION, "1.00", "0");
        incorrectValues(DIVISION, "0.001", "0");
    }

    @Test
    public void testAccuracy() {
        test(MULTIPLICATION, "111111111", "111111111", "1.234567898765432E16");
        test(MULTIPLICATION, "12345679", "9", "111111111");
    }

    private void incorrectValues(Operation operationName, String numberOne, String numberTwo) {
        //given
        firstNumber = new BigDecimal(numberOne);
        secondNumber = new BigDecimal(numberTwo);

        try {
            //when
            CALCULATOR.calculate(operationName, firstNumber, secondNumber);
            fail("Arithmetic expression: numberOne: " + numberOne + ", operation: " + operationName + ", numberTwo: " + numberTwo + " is valid!");
        } catch (UndefinedNumberException | IllegalArgumentException e) {
            //Ignore
        }
    }

    private void test(Operation operationName, String numberOne, String numberTwo, String expectedResult) {
        //given
        firstNumber = new BigDecimal(numberOne);
        secondNumber = new BigDecimal(numberTwo);

        //when
        BigDecimal result = null;
        try {
            result = CALCULATOR.calculate(operationName, firstNumber, secondNumber);
        } catch (UndefinedNumberException e) {
            fail("Fail test param: " + operationName + " " + numberOne + " " + numberTwo);
        }
        String actualResult = convertBigDecimalToString(result);

        //then
        assertEquals(expectedResult, actualResult);
    }

    private String convertBigDecimalToString(BigDecimal result) {
        if (result.compareTo(maxValue) > 0 || result.compareTo(minValue) < 0) {
            return result.stripTrailingZeros().toString().replace("E+", "E");
        }
        return result.stripTrailingZeros().toPlainString();
    }
}
