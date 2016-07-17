package com.malex;

import javafx.scene.Parent;
import javafx.scene.control.TextField;
import org.junit.BeforeClass;
import org.junit.Test;
import org.loadui.testfx.GuiTest;
import org.loadui.testfx.utils.FXTestUtils;

import java.util.concurrent.TimeUnit;

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
        testCalculate("~1-3=", "-2");
        testCalculate("1~-3=", "-4");
        testCalculate("1~-3~=", "2");
    }

    @Test
    public void testSetComma(){
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
    public void testAccuracy() { //TODO http://chto-zachem-pochemu.ru/kak-proverit-tochnost-kalkyliatora/
        testCalculate("111111111*111111111=", "12345678987654321");
        testCalculate("12345679*9=", "111111111");
    }

    @Test
    public void testNumberAndMoreOperators() {
        testCalculate("1---", "1");
        testCalculate("1+++", "1");
        testCalculate("1***", "1");
        testCalculate("1///", "1");

        testCalculate("1+========", "9");
        testCalculate("1-=======", "-6");
        testCalculate("1*=======", "1");
        testCalculate("1/=======", "1");

        testCalculate("2*====", "32");
        testCalculate("2/=", "1");
    }

    private void testCalculate(String arithmeticExpression, String expectedResult) {
        //#Step: 1. Clear display
        controller.sleep(50, TimeUnit.MILLISECONDS);
        controller.click("#reset");

        //#Step: 2. Click on buttons
        for (char button : arithmeticExpression.toCharArray()) {
            controller.click(findKey(button));
        }

        //# Step: 3. Get result on display
        TextField display = GuiTest.find("#display");
        String actualResult = display.getText();

        //# Step: 4. Compare the expected results with the actual result.
        controller.sleep(100, TimeUnit.MILLISECONDS);
        assertEquals(expectedResult, actualResult);
    }

    // Поиск ид кнопки по символу.
    // Знак '+/-' еквивалентен '~'
    private String findKey(char key) {
        switch (key) {
            case '0':
                return "#zero";
            case '1':
                return "#one";
            case '2':
                return "#two";
            case '3':
                return "#three";
            case '4':
                return "#four";
            case '5':
                return "#five";
            case '6':
                return "#six";
            case '7':
                return "#seven";
            case '8':
                return "#eight";
            case '9':
                return "#nine";

            case '+':
                return "#add";
            case '-':
                return "#subtraction";
            case '*':
                return "#multiplication";
            case '/':
                return "#division";
            case '=':
                return "#equals";


            case '~':
                return "#sign";
            case '.':
                return "#comma";
        }
        throw new IllegalArgumentException("The button : \'" + key + "\'" + " cannot be found!!!");
    }

}
