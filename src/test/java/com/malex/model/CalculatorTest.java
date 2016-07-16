package com.malex.model;

import com.malex.service.impl.AddOperation;
import com.malex.service.impl.DivisionOperation;
import com.malex.service.impl.MultiplicationOperation;
import com.malex.service.impl.SubtractionOperation;
import org.junit.BeforeClass;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;

public class CalculatorTest {

    private static Calculator calculator;

    @BeforeClass
    public static void init() {
        calculator = new Calculator();
        calculator.addOperation(new AddOperation());
        calculator.addOperation(new DivisionOperation());
        calculator.addOperation(new SubtractionOperation());
        calculator.addOperation(new MultiplicationOperation());
    }

    /**
     * Оперции доступные по id:
     * add
     * subtraction
     * division
     * multiplication
     */
    @Test
    public void testNumbers() {
        test("add", 1, 2, 3);

        test("subtraction", 1, 2, -1);

        test("division", 1, 2, 0);
        test("division", 0, 1, 0);
        test("division", 1, 0, 0);

        test("multiplication", 1, 2, 2);
    }

    @Test
    public void testIncorrectValues() {
        // некорректное значение ператора
        incorrectValues("", 1, 2);
        incorrectValues(null, 1, 2);
        incorrectValues("xxxxxxx", 1, 2);
        incorrectValues("ADD", 1, 2);
    }

    private void incorrectValues(String operationName, int numberOne, int numberTwo) {
        try {
            //when
            calculator.calculate(operationName, numberOne, numberTwo);
            fail("Arithmetic expression: numberOne: " + numberOne + ", operation: " + operationName + ", numberTwo: " + numberTwo + " is valid!");
        } catch (ArithmeticException | IllegalArgumentException e) {
            //Ignore
        }
    }

    private void test(String operationName, int numberOne, int numberTwo, int expectedResult) {
        //when
        int actualResult = calculator.calculate(operationName, numberOne, numberTwo);

        //then
        assertEquals(expectedResult, actualResult);
    }
}
