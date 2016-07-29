package com.malex;

import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import org.junit.BeforeClass;
import org.junit.Test;
import org.loadui.testfx.GuiTest;
import org.loadui.testfx.utils.FXTestUtils;

import static junit.framework.TestCase.assertEquals;

public class MainAppKeyTest {

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
    public void testSimply() {
        testCalculateKeyCode("1234567890", "1234567890");
        testCalculateKeyCode("1", "1");
        testCalculateKeyCode("2", "2");
        testCalculateKeyCode("3", "3");
        testCalculateKeyCode("4", "4");
        testCalculateKeyCode("5", "5");
        testCalculateKeyCode("6", "6");
        testCalculateKeyCode("7", "7");
        testCalculateKeyCode("8", "8");
        testCalculateKeyCode("9", "9");
        testCalculateKeyCode("0", "0");
    }

    @Test
    public void testSimplyArithmeticOperations() {
        // positive numbers
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
        testCalculateKeyCode("0-2-3-4-5-6==", "-26");

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

        testCalculateKeyCode("11111111111111111111111111111111111111111999999..............", "111111111111111111111111111111");
        testCalculateKeyCode("11111111111111111111111111111111111111111999999~..............", "-111111111111111111111111111111");
        testCalculateKeyCode("11111111111111111111111111111111111111111999999..............=", "111111111111111111111111111111");
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

        testCalculateKeyCode("33333333/88888888====", "0");
        testCalculateKeyCode("999999999999999999999999999999%=", "1E28");
        testCalculateKeyCode("1-999999999999999999999999999999%=", "-1E28");
        testCalculateKeyCode("11111111111111111111111111111111231242355353345345345435356363%=", "1.111111111111111E27");
    }

    @Test
    public void testAfterPercent() {
        testCalculateKeyCode("2-52%+3*4=", "12.96");
        testCalculateKeyCode("2-52%+3*4-111.2222=", "-98.2622");
        testCalculateKeyCode("2-52%+3*4/2=", "6.96");
    }

    @Test
    public void testAfterEquals() {
        testCalculateKeyCode("2+3=+2*3=", "11");
        testCalculateKeyCode("0+1=+2+2+2*3/2=", "8");
        testCalculateKeyCode("1/1=+2+2+2*3/2=", "8");
        testCalculateKeyCode("1*1=+2+2+2*3/2=", "8");
        testCalculateKeyCode("1/0+1=", "2");
        testCalculateKeyCode("3+5=.0+0=", "8");
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
        testCalculateKeyCode("111111111*111111111=", "1.234567898765432E16");
        testCalculateKeyCode("12345679*9=", "111111111");
        testCalculateKeyCode("10/3*3=", "9.99999999999999");
        testCalculateKeyCode("10/3=*3=", "9.99999999999999");
        testCalculateKeyCode("9/7*7=", "9.00000000000003");
        testCalculateKeyCode("100*10/2+16*4=", "564");
        testCalculateKeyCode("12345679*27=", "333333333");
        testCalculateKeyCode("12345679*8=", "98765432");

        testCalculateKeyCode("1111*1111=", "1234321");
        testCalculateKeyCode("123123123/1001001=", "123");
        testCalculateKeyCode("12345679*7.2=", "88888888.8");
        testCalculateKeyCode("12345678-123456789=", "-111111111");
        testCalculateKeyCode("12345679*9=/9=", "12345679");

        testCalculateKeyCode("12345679*9=", "111111111");
        testCalculateKeyCode("12345679*18=", "222222222");
        testCalculateKeyCode("12345679*27=", "333333333");
        testCalculateKeyCode("12345679*81=", "999999999");
    }

    @Test
    public void testEngineeringPreview() {
        testCalculateKeyCode("999999999999999+1=", "1000000000000000");
        testCalculateKeyCode("9999999999999999+1=", "1E16");

        testCalculateKeyCode("66666666*666666666=", "4.444444395555556E16");
        testCalculateKeyCode("123456789*=================", "4.438841002465506E145");
        testCalculateKeyCode("4444444*===", "3.90184267032488E26");
        testCalculateKeyCode("0.123*0.23=====================", "0");

        testCalculateKeyCode("1/999/999/999/999/999=", "0");
    }

    @Test
    public void testLimitInput() {
        // positive number
        testCalculateKeyCode("999999999999999999999999999991224233223432+1=", "1E30");
        testCalculateKeyCode("999999999999999999999999999999991224233223432*11111111111111111111111111111111111111111-222222222222222222222222222222222222=", "1.111111111111111E59");
        testCalculateKeyCode("1~*2+3+99999999999999999999999999999999999999999=", "1E30");
        testCalculateKeyCode("9.999999999999999999999999999934224233223432+1=", "11");
        testCalculateKeyCode("0.0000000000000000000000000001+1=", "1");
        testCalculateKeyCode("0.0000000000000000000000000001+1=====", "5");

        // negative number
        testCalculateKeyCode("2222222222222222222222222222222222~+222222222222222222222222222222222222222=", "0");
        testCalculateKeyCode("222222222222222222~2222222222222222+222222222222222222222222222222222222222=", "2E29");
        testCalculateKeyCode("0.0000000000000000000000000001~-1=", "-1");
        testCalculateKeyCode("0.0000000000000000000000000001~-1==", "-2");
    }

    @Test
    public void testLimitInputUndefinedNumber(){
        testCalculateKeyCode("99999999999999999*99999999999999999999=============", "Undefined"); // Up Limit 1E150
        testCalculateKeyCode("99999999999.999999999999*9999999.9999999999999=====================", "Undefined");
        testCalculateKeyCode("0.1111112*9999999.9999999999999+923849235958985*777732372373727327237=====================", "Undefined");

        testCalculateKeyCode("1111111111111111111~*99999999999999999999============", "Undefined"); // DOWN Limit -1E150
        testCalculateKeyCode("1111111111111111111~*99999999999999999999/322222222222222*88887777723737737373737============", "Undefined");
        testCalculateKeyCode("1111111111111111111~*99999999999999999999/322222222222222*88887777723737737373737============", "Undefined");

        testCalculateKeyCode("99999999999999999*99999999999999999999============+1=", "2");
        testCalculateKeyCode("99999999999999999*99999999999999999999============*1=", "1");
        testCalculateKeyCode("99999999999999999*99999999999999999999============/1=", "1");
    }

    @Test
    public void testButtonMemory() {
        // simply operation
        testCalculateKeyCode("cr", "0");
        testCalculateKeyCode("56r", "0");
        testCalculateKeyCode("2pppppp", "2");
        testCalculateKeyCode("2mmmmmm", "2");
        testCalculateKeyCode("c5pmr", "0");
        testCalculateKeyCode("c5pmmr", "-5");
        testCalculateKeyCode("c5mpr", "0");

        testCalculateKeyCode("c1prar", "1");
        testCalculateKeyCode("c1.001pr456ar", "1.001");
        testCalculateKeyCode("c2~.001p123", "123");
        testCalculateKeyCode("c00.001~p123r", "-0.001");

        // replace operation
        testCalculateKeyCode("c2ppppppppppr", "20");
        testCalculateKeyCode("c2mmmmmmmmmmr", "-20");

        // memory operation and arithmetic operation
        testCalculateKeyCode("c5p63+r=", "68");
        testCalculateKeyCode("c5m63+r=", "58");
        testCalculateKeyCode("c56p2+6-r=", "-48");
        testCalculateKeyCode("c99999999999999999999999999999999999999999999999999999p1+r=", "1E30");
        testCalculateKeyCode("c99999999999999999999999999999999999999999999999999999p1pr", "1E30");
    }

    @Test
    public void testArithmeticOperationAndButtonMemory() {
        testCalculateKeyCode("c234p10/2+3-5+r=", "237");
        testCalculateKeyCode("c14/2=p-7=7-r=", "0");
        testCalculateKeyCode("c2*3=p-10=p-3=", "-7");
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
        testCalculateKeyCode("0.99+3*4-2/0=", "Undefined");
    }

    private void testCalculateKeyCode(String arithmeticExpression, String expectedResult) {
        //#Step: 1. Clear DISPLAY
        controller.push(KeyCode.ESCAPE);

        //#Step: 2. Click on buttons
        for (char button : arithmeticExpression.toCharArray()) {
            pushKey(button);
        }

        //# Step: 3. Get result on DISPLAY
        TextField display = GuiTest.find("#DISPLAY");
        String actualResult = display.getText();

        //# Step: 4. Compare the expected results with the actual result.
        assertEquals(expectedResult, actualResult);
    }

    /**
     * Search by ID button.
     * <p>
     * The designations:
     * Value '+/-' equals '~'
     * Value 'a' equals 'reset'
     * Value 'm+' equals 'p'
     * Value 'm-' equals 'm'
     * Value 'mc' equals 'c'
     * Value 'mr' equals 'r'
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
                controller.push(KeyCode.DIGIT5, KeyCode.SHIFT);
                return;
            case '~':
                controller.push(KeyCode.MINUS, KeyCode.SHIFT);
                return;
            case '.':
                controller.push(KeyCode.COMMA);
                return;
            case 'a':
                controller.push(KeyCode.ESCAPE);
                return;
            case 'p':
                controller.push(KeyCode.P);
                return;
            case 'm':
                controller.push(KeyCode.M);
                return;
            case 'c':
                controller.push(KeyCode.C);
                return;
            case 'r':
                controller.push(KeyCode.R);
                return;
        }
        throw new IllegalArgumentException("The button : \'" + value + "\'" + " cannot be found!!!");
    }

}
