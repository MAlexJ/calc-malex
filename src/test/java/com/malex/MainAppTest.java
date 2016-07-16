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
    public void test() {
        testCalculate("1234567890", "1234567890");
        testCalculate("1+1=", "2");
        testCalculate("1-1=", "0");
        testCalculate("1-3=", "-2");
    }

    @Test
    public void test4() {
        testCalculate("~1-3=", "-2");
        testCalculate("1~-3=", "-4");
        testCalculate("1~-3~=", "2");
    }

    @Test
    public void test2() {
        testCalculate("1---", "1");
        testCalculate("1+++", "1");
        testCalculate("1***", "1");
        testCalculate("1///", "1");
    }

    @Test
    public void test3() {
        testCalculate("1+=======", "8");
//        testCalculate("1-=======", "-7");
        testCalculate("1*=======", "1");
        testCalculate("1/=======", "1");

        testCalculate("2*====", "32");
        testCalculate("2/=", "1");
    }

    private void testCalculate(String arithmeticExpression, String expectedResult) {
        //#Step: 1. Clear display
        controller.sleep(100, TimeUnit.MILLISECONDS);
        controller.click("#reset");
        controller.sleep(100, TimeUnit.MILLISECONDS);

        //#Step: 2. Click on buttons
        for (char button : arithmeticExpression.toCharArray()) {
            controller.sleep(100, TimeUnit.MILLISECONDS);
            controller.click(findKey(button));
            controller.sleep(100, TimeUnit.MILLISECONDS);
        }

        //# Step: 3. Get result on display
        TextField display = GuiTest.find("#display");
        String actualResult = display.getText();

        //# Step: 4. Compare the expected results with the actual result.
        controller.sleep(200, TimeUnit.MILLISECONDS);
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
