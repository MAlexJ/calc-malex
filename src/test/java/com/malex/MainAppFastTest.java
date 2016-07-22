package com.malex;

import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import org.junit.BeforeClass;
import org.junit.Test;
import org.loadui.testfx.GuiTest;
import org.loadui.testfx.utils.FXTestUtils;

import static junit.framework.TestCase.assertEquals;

public class MainAppFastTest {

    private static GuiTest controller;

    @BeforeClass
    public static void setUpClass() throws InterruptedException {
        FXTestUtils.launchApp(MainApp.class);
        controller = new GuiTest() {
            @Override
            protected Parent getRootNode() {
                return stage.getScene().getRoot();
            }
        };
    }

    @Test
    public void testSimplyArithmeticOperations() {
        // positive numbers
        testCalculateKeyCode("1234567890", "1234567890");
        testCalculateKeyCode("1+1=", "2");
        testCalculateKeyCode("1-1=", "0");
        testCalculateKeyCode("1-3=", "-2");

        // negative numbers
        testCalculateKeyCode("1234567890~", "-1234567890");
        testCalculateKeyCode("~1-3=", "-2");
        testCalculateKeyCode("1~-3=", "-4");
        testCalculateKeyCode("1~-3~=", "2");

        // positive numbers float
        testCalculateKeyCode("123456.7890", "123456.7890");
        testCalculateKeyCode("1.01+1.1=", "2.11");
        testCalculateKeyCode("1-0.99=", "0.01");
        testCalculateKeyCode("1.01-3.01=", "-2");
    }

    @Test
    public void testArithmeticOperationsWithoutPriorityOperation() {
        testCalculateKeyCode("1+2+3+4+5+6=", "21");
        testCalculateKeyCode("1+2+3+4+5+6==", "27");

        testCalculateKeyCode("1-2-3-4-5-6=", "-19");
        testCalculateKeyCode("1-2-3-4-5-6==", "-25");

        testCalculateKeyCode("1+2+3-5+1-3=", "-1");
        testCalculateKeyCode("1~-2+3-4-5=", "-9");

        testCalculateKeyCode("1*2*3*4*5*6=", "720");
        testCalculateKeyCode("1*2*3*4*5*6==", "4320");

        testCalculateKeyCode("2*2/4=", "1");
        testCalculateKeyCode("2*2/4*1*10=", "10");

        testCalculateKeyCode("2*3/2+2=", "5");
        testCalculateKeyCode("8/4-2=", "0");
    }

    @Test
    public void testArithmeticOperationsPriorityOperation() {
        //simply
        testCalculateKeyCode("1+2*3=", "7");
        testCalculateKeyCode("1+2*3+1=", "8");
        testCalculateKeyCode("1+4*3+1+2=", "16");
        testCalculateKeyCode("1+4*3+1+2*3=", "20");
        testCalculateKeyCode("1+4*3-1+2*3=", "18");
        testCalculateKeyCode("1+4*3-1=", "12");
        testCalculateKeyCode("1+4*3*1-1=", "12");
        testCalculateKeyCode("1+4*3*2/2=", "13");
        testCalculateKeyCode("1+4*3*2-1=", "24");
        testCalculateKeyCode("1+4*3*2-1+2=", "26");
        testCalculateKeyCode("1+4*3*2-1+2/2+1=", "26");

        //middle
        testCalculateKeyCode("4/6*7-89=", "-84.33333333333331");
        testCalculateKeyCode("543.02*0.02+48.33=", "59.1904");
        testCalculateKeyCode("2+2+2+2+2+2+2+2+2+2+2*3=", "26");
    }

    @Test
    public void testSetComma() {
        testCalculateKeyCode("1..............", "1.");
        testCalculateKeyCode("1.23....", "1.23");
        testCalculateKeyCode("0.00....", "0.00");
        testCalculateKeyCode(".00....", "0.00");

        testCalculateKeyCode(".00+", "0.00");
        testCalculateKeyCode(".00-", "0.00");
        testCalculateKeyCode(".00*", "0.00");
        testCalculateKeyCode(".00/", "0.00");

        testCalculateKeyCode(".000~", "0");
        testCalculateKeyCode("0.~", "0");
        testCalculateKeyCode(".00=", "0");
        testCalculateKeyCode("000.00", "0.00");
        testCalculateKeyCode("01234", "1234");
    }

    @Test
    public void testPercent() {
        testCalculateKeyCode("1%", "0.01");
        testCalculateKeyCode("1%=", "0.01");
        testCalculateKeyCode("1%===", "0.01");
        testCalculateKeyCode("1%%", "0.0001");
        testCalculateKeyCode("9%%%", "0.000009");
        testCalculateKeyCode("1%++++", "0.01");
        testCalculateKeyCode("1%+=========", "0.1");

        testCalculateKeyCode("1+2%", "0.02");
        testCalculateKeyCode("1-2%", "0.02");
        testCalculateKeyCode("1*2%", "0.02");
        testCalculateKeyCode("1/2%", "0.02");

        testCalculateKeyCode("1+2%%", "0.0002");
        testCalculateKeyCode("1-2%=====", "0.9");
        testCalculateKeyCode("1+2%==", "1.04");
        testCalculateKeyCode("1+2%*3=", "1.06");

        testCalculateKeyCode("0-2%", "0");
        testCalculateKeyCode("0-2%=", "0");
        testCalculateKeyCode("2-50%", "1");
        testCalculateKeyCode("2-90%", "1.8");
        testCalculateKeyCode("5-50%", "2.5");
        testCalculateKeyCode("11111111-50%", "5555555.5");
        testCalculateKeyCode("2~-50%", "-1");
        testCalculateKeyCode("2-50~%", "-1");
    }

    @Test
    public void testAfterPercent() {
        testCalculateKeyCode("2-52%+3*4=", "12.96");
        testCalculateKeyCode("2-52%+3*4-111.2222=", "-98.2622");
        testCalculateKeyCode("2-52%+3*4/2=", "6.96");
    }

    @Test
    public void testLimitResultDivisionViewDisplay() {
        testCalculateKeyCode("4/6=", "0.66666666666667");
        testCalculateKeyCode("4~/6=", "-0.66666666666667");
        testCalculateKeyCode("4/6~=", "-0.66666666666667");

        testCalculateKeyCode("1/3=", "0.33333333333333");
        testCalculateKeyCode("1/6=", "0.16666666666667");
        testCalculateKeyCode("1/7=", "0.14285714285714");
        testCalculateKeyCode("1/9=", "0.11111111111111");
        testCalculateKeyCode("2/7=", "0.28571428571429");
        testCalculateKeyCode("2/9=", "0.22222222222222");
        testCalculateKeyCode("3/7=", "0.42857142857143");
    }

    @Test
    public void testAccuracy() {
        testCalculateKeyCode("111111111*111111111=", "12345678987654321");
        testCalculateKeyCode("12345679*9=", "111111111");
    }

    @Test
    public void testNumberAndMoreOperators() {
        testCalculateKeyCode("1---", "1");
        testCalculateKeyCode("1+++", "1");
        testCalculateKeyCode("1***", "1");
        testCalculateKeyCode("1///", "1");

        testCalculateKeyCode("1+=========", "10");
        testCalculateKeyCode("1-=======", "-6");
        testCalculateKeyCode("1*=======", "1");
        testCalculateKeyCode("1/=======", "1");

        testCalculateKeyCode("2*====", "32");
        testCalculateKeyCode("2/=", "1");
    }

    @Test
    public void testChangeOperation() {
        testCalculateKeyCode("2/*-+=", "4");
        testCalculateKeyCode("2+-*3-+3=", "9");
        testCalculateKeyCode("2+-*3*-+3=", "9");
    }

    @Test
    public void testDisplayUndefined() {
        testCalculateKeyCode("2/0=", "Undefined");
        testCalculateKeyCode("2/0+45=", "90");
        testCalculateKeyCode("2/0*45=", "2025");
    }


    private void testCalculateKeyCode(String arithmeticExpression, String expectedResult) {
        //#Step: 1. Clear display
        controller.sleep(100);
        controller.push(KeyCode.ESCAPE);

        //#Step: 2. Click on buttons
        for (char button : arithmeticExpression.toCharArray()) {
            pushKey(button);
        }

        //# Step: 3. Get result on display
        TextField display = GuiTest.find("#display");
        String actualResult = display.getText();

        //# Step: 4. Compare the expected results with the actual result.
        assertEquals(expectedResult, actualResult);
    }


    /**
     * // Поиск ид кнопки по символу.
     * Принятые обозначения:
     * Знак '+/-' еквивалентен '~'
     * Знак 'a' еквивалентен 'reset'
     * Знак 'm+' еквивалентен 'p'
     * Знак 'm-' еквивалентен 'm'
     * Знак 'mc' еквивалентен 'c'
     * Знак 'mr' еквивалентен 'r'
     */
    private void pushKey(char value) {
        switch (value) {
            case '0':
                controller.push(KeyCode.DIGIT0);
                return;
            case '1':
                controller.push(KeyCode.DIGIT1);
                return;
            case '2':
                controller.push(KeyCode.DIGIT2);
                return;
            case '3':
                controller.push(KeyCode.DIGIT3);
                return;
            case '4':
                controller.push(KeyCode.DIGIT4);
                return;
            case '5':
                controller.push(KeyCode.DIGIT5);
                return;
            case '6':
                controller.push(KeyCode.DIGIT6);
                return;
            case '7':
                controller.push(KeyCode.DIGIT7);
                return;
            case '8':
                controller.push(KeyCode.DIGIT8);
                return;
            case '9':
                controller.push(KeyCode.DIGIT9);
                return;
            case '+':
                controller.sleep(20);
                controller.push(KeyCode.EQUALS, KeyCode.SHIFT);
                return;
            case '-':
                controller.push(KeyCode.MINUS);
                return;
            case '*':
                controller.sleep(20);
                controller.push(KeyCode.DIGIT8, KeyCode.SHIFT);
                return;
            case '/':
                controller.push(KeyCode.SLASH);
                return;
            case '=':
                controller.push(KeyCode.ENTER);
                return;
            case '%':
                controller.sleep(30);
                controller.push(KeyCode.DIGIT5, KeyCode.SHIFT);
                return;
            case '~':
                controller.sleep(20);
                controller.push(KeyCode.MINUS, KeyCode.SHIFT);
                return;
            case '.':
                controller.push(KeyCode.COMMA);
                return;
            case 'a':
                controller.push(KeyCode.ESCAPE);
                return;
            case 'p':
                return;
            case 'm':
                return;
            case 'c':
                return;
            case 'r':
                return;
        }
        throw new IllegalArgumentException("The button : \'" + value + "\'" + " cannot be found!!!");
    }

}
