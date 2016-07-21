package com.malex;

import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import org.junit.BeforeClass;
import org.junit.Test;
import org.loadui.testfx.GuiTest;
import org.loadui.testfx.utils.FXTestUtils;

import static junit.framework.TestCase.assertEquals;

public class MainAppTest {

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
        testCalculate("1234567890", "1234567890");
        testCalculate("1+1=", "2");
        testCalculate("1-1=", "0");
        testCalculate("1-3=", "-2");

        // negative numbers
        testCalculate("1234567890~", "-1234567890");
        testCalculate("~1-3=", "-2");
        testCalculate("1~-3=", "-4");
        testCalculate("1~-3~=", "2");

        // positive numbers float
        testCalculate("123456.7890", "123456.7890");
        testCalculate("1.01+1.1=", "2.11");
        testCalculate("1-0.99=", "0.01");
        testCalculate("1.01-3.01=", "-2");
    }

    @Test
    public void testArithmeticOperationsWithoutPriorityOperation() {
        testCalculate("1+2+3+4+5+6=", "21");
        testCalculate("1+2+3+4+5+6==", "27");

        testCalculate("1-2-3-4-5-6=", "-19");
        testCalculate("1-2-3-4-5-6==", "-25");

        testCalculate("1+2+3-5+1-3=", "-1");
        testCalculate("1~-2+3-4-5=", "-9");

        testCalculate("1*2*3*4*5*6=", "720");
        testCalculate("1*2*3*4*5*6==", "4320");

        testCalculate("2*2/4=", "1");
        testCalculate("2*2/4*1*10=", "10");

        testCalculate("2*3/2+2=", "5");
        testCalculate("8/4-2=", "0");
    }

    @Test
    public void testArithmeticOperationsPriorityOperation() {
        //simply
        testCalculate("1+2*3=", "7");
        testCalculate("1+2*3+1=", "8");
        testCalculate("1+4*3+1+2=", "16");
        testCalculate("1+4*3+1+2*3=", "20");
        testCalculate("1+4*3-1+2*3=", "18");
        testCalculate("1+4*3-1=", "12");
        testCalculate("1+4*3*1-1=", "12");
        testCalculate("1+4*3*2/2=", "13");
        testCalculate("1+4*3*2-1=", "24");
        testCalculate("1+4*3*2-1+2=", "26");
        testCalculate("1+4*3*2-1+2/2+1=", "26");

        //middle
        testCalculate("4/6*7-89=", "-84.33333333333331");
        testCalculate("543.02*0.02+48.33=", "59.1904");
    }

    @Test
    public void testSetComma() {
        testCalculate("1..............", "1.");
        testCalculate("1.23....", "1.23");
        testCalculate("0.00....", "0.00");
        testCalculate(".00....", "0.00");

        testCalculate(".00+", "0.00");
        testCalculate(".00-", "0.00");
        testCalculate(".00*", "0.00");
        testCalculate(".00/", "0.00");

        testCalculate(".000~", "0");
        testCalculate("0.~", "0");
        testCalculate(".00=", "0");
        testCalculate("000.00", "0.00");
        testCalculate("01234", "1234");
    }

    @Test
    public void testPercent() {
        testCalculate("1%", "0.01");
        testCalculate("1%=", "0.01");
        testCalculate("1%===", "0.01");
        testCalculate("1%%", "0.0001");
        testCalculate("9%%%", "0.000009");
        testCalculate("1%++++", "0.01");
        testCalculate("1%+=========", "0.1");

        testCalculate("1+2%", "0.02");
        testCalculate("1-2%", "0.02");
        testCalculate("1*2%", "0.02");
        testCalculate("1/2%", "0.02");

        testCalculate("1+2%%", "0.0002");
        testCalculate("1-2%=====", "0.9");
        testCalculate("1+2%==", "1.04");
        testCalculate("1+2%*3=", "1.06");

        testCalculate("0-2%", "0");
        testCalculate("0-2%=", "0");
        testCalculate("2-50%", "1");
        testCalculate("2-90%", "1.8");
        testCalculate("5-50%", "2.5");
        testCalculate("11111111-50%", "5555555.5");
        testCalculate("2~-50%", "-1");
        testCalculate("2-50~%", "-1");
    }

    @Test
    public void testAfterPercent() {
        testCalculate("2-52%+3*4=", "12.96");
        testCalculate("2-52%+3*4-111.2222=", "-98.2622");
        testCalculate("2-52%+3*4/2=", "6.96");
    }

//    @Test
//    public void testAfterEquals() {
//        testCalculate("9+2=-3=", "8"); //TODO -валюсь сдесь!!!!
//    }

    @Test
    public void testButtonMemory() {
        // simply operation
        testCalculate("r", "0");
        testCalculate("56r", "0");
        testCalculate("2pppppp", "2");
        testCalculate("2mmmmmm", "2");
        testCalculate("c5pmr", "0");
        testCalculate("c5pmmr", "-5");
        testCalculate("c5mpr", "0");

        testCalculate("c1prar", "1");
        testCalculate("c1.001pr456ar", "1.001");
        testCalculate("c2~.001p123", "123");
        testCalculate("c00.001~p123r", "-0.001");

        // replace operation
        testCalculate("c2ppppppppppr", "20");
        testCalculate("c2mmmmmmmmmmr", "-20");

        // memory operation and arithmetic operation
        testCalculate("c5p63+r=", "68");
        testCalculate("c5m63+r=", "58");
    }

    @Test
    public void testButtonMemoryWithArithmeticOperations() {
        // TODO !!!!!
//        testCalculate("2+2+2+2+2+2+2+2+2+2+2*2=", "24");
        testCalculate("6/3=5*2=", "10");
    }

    @Test
    public void testLimitResultDivisionViewDisplay() {
        testCalculate("4/6=", "0.66666666666667");
        testCalculate("4~/6=", "-0.66666666666667");
        testCalculate("4/6~=", "-0.66666666666667");

        testCalculate("1/3=", "0.33333333333333");
        testCalculate("1/6=", "0.16666666666667");
        testCalculate("1/7=", "0.14285714285714");
        testCalculate("1/9=", "0.11111111111111");
        testCalculate("2/7=", "0.28571428571429");
        testCalculate("2/9=", "0.22222222222222");
        testCalculate("3/7=", "0.42857142857143");
    }

    @Test
    public void testAccuracy() {
        testCalculate("111111111*111111111=", "12345678987654321");
        testCalculate("12345679*9=", "111111111");
    }

    @Test
    public void testNumberAndMoreOperators() {
        testCalculate("1---", "1");
        testCalculate("1+++", "1");
        testCalculate("1***", "1");
        testCalculate("1///", "1");

        testCalculate("1+=========", "10");
        testCalculate("1-=======", "-6");
        testCalculate("1*=======", "1");
        testCalculate("1/=======", "1");

        testCalculate("2*====", "32");
        testCalculate("2/=", "1");
    }

    @Test
    public void testChangeOperation() {
        testCalculate("2/*-+=", "4");
        testCalculate("2+-*3-+3=", "9");
        testCalculate("2+-*3*-+3=", "9");
    }

    @Test
    public void testDisplayUndefined() {
        testCalculate("2/0=", "Undefined");
        testCalculate("2/0+45=", "90");
        testCalculate("2/0*45=", "2025");
    }

//    @Test
//    public void teRstTTT() {
//
//
//        controller.push(KeyCode.DIGIT1);
//        controller.sleep(10);
//        controller.push(KeyCode.DIGIT2);
//        controller.sleep(10);
//        controller.push(KeyCode.DIGIT3);
//        controller.sleep(10);
//        controller.push(KeyCode.DIGIT4);
//        controller.sleep(10);
//        controller.push(KeyCode.DIGIT5);
//        controller.sleep(10);
//        controller.push(KeyCode.DIGIT6);
//        controller.sleep(10);
//        controller.push(KeyCode.DIGIT7);
//        controller.sleep(10);
//        controller.push(KeyCode.DIGIT8);
//        controller.sleep(10);
//        controller.push(KeyCode.DIGIT9);
//        controller.sleep(10);
////        controller.press(KeyCode.SHIFT);
//        controller.push( KeyCode.DIGIT5,KeyCode.SHIFT);
//
//        controller.sleep(10);
//        controller.push(KeyCode.DIGIT9);
//        controller.sleep(3000);
//
//    }

    private void testCalculateKeyKeyCode(String arithmeticExpression, String expectedResult) {
        //#Step: 1. Clear display

        //#Step: 2. Click on buttons

        //# Step: 3. Get result on display

        //# Step: 4. Compare the expected results with the actual result.

    }

    private void testCalculate(String arithmeticExpression, String expectedResult) {
        //#Step: 1. Clear display
        controller.click("#ESCAPE");

        //#Step: 2. Click on buttons
        for (char button : arithmeticExpression.toCharArray()) {
            controller.click(findKey(button));
        }

        //# Step: 3. Get result on display
        TextField display = GuiTest.find("#display");
        String actualResult = display.getText();

        //# Step: 4. Compare the expected results with the actual result.
        assertEquals(expectedResult, actualResult);
    }

    // Поиск ид кнопки по символу.
    // Принятые обозначения:
    // Знак '+/-' еквивалентен '~'
    // Знак 'a' еквивалентен 'reset'
    // Знак 'm+' еквивалентен 'p'
    // Знак 'm-' еквивалентен 'm'
    // Знак 'mc' еквивалентен 'c'
    // Знак 'mr' еквивалентен 'r'
    private String findKey(char key) {
        switch (key) {
            case '0':
                return "#DIGIT0";
            case '1':
                return "#DIGIT1";
            case '2':
                return "#DIGIT2";
            case '3':
                return "#DIGIT3";
            case '4':
                return "#DIGIT4";
            case '5':
                return "#DIGIT5";
            case '6':
                return "#DIGIT6";
            case '7':
                return "#DIGIT7";
            case '8':
                return "#DIGIT8";
            case '9':
                return "#DIGIT9";
            case '+':
                return "#ADD";
            case '-':
                return "#MINUS";
            case '*':
                return "#MULTIPLICATION";
            case '/':
                return "#SLASH";
            case '=':
                return "#EQUALS";
            case '%':
                return "#PERCENT";
            case '~':
                return "#SIGN";
            case '.':
                return "#COMMA";
            case 'a':
                return "#ESCAPE";
            case 'p':
                return "#m_plus";
            case 'm':
                return "#m_minus";
            case 'c':
                return "#mc";
            case 'r':
                return "#mr";
        }
        throw new IllegalArgumentException("The button : \'" + key + "\'" + " cannot be found!!!");
    }

}
