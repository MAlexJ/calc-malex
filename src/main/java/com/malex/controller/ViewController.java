package com.malex.controller;

import com.malex.MainApp;
import com.malex.model.Calculator;
import com.malex.service.impl.AddOperation;
import com.malex.service.impl.DivisionOperation;
import com.malex.service.impl.MultiplicationOperation;
import com.malex.service.impl.SubtractionOperation;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;

public class ViewController {

    @FXML
    private TextField display;

    @FXML
    private Button zero;
    @FXML
    private Button one;
    @FXML
    private Button two;
    @FXML
    private Button three;
    @FXML
    private Button four;
    @FXML
    private Button five;
    @FXML
    private Button six;
    @FXML
    private Button seven;
    @FXML
    private Button eight;
    @FXML
    private Button nine;

    @FXML
    private Button add;
    @FXML
    private Button subtraction;
    @FXML
    private Button multiplication;
    @FXML
    private Button division;
    @FXML
    private Button percent;
    @FXML
    private Button reset;
    @FXML
    private Button sign;
    @FXML
    private Button comma;
    @FXML
    private Button equals;


    // номер в памяти
    private int number;

    // индикация для следующего числа
    private boolean nextNumber;

    // Модель
    private static Calculator calculator;

    private String operator = "";

    // Инициализация модели
    public ViewController() {
        calculator = new Calculator();
        calculator.addOperation(new AddOperation());
        calculator.addOperation(new DivisionOperation());
        calculator.addOperation(new SubtractionOperation());
        calculator.addOperation(new MultiplicationOperation());

    }

    private MainApp mainApp;

    // Инициализация котроллера
    public void init(MainApp mainApp) {
        this.mainApp = mainApp;
        this.display.setEditable(false);
        this.display.setText(START_CURSOR_POSITION);
    }

    // Обработчик кнопок
    @FXML
    public void handlerNumbers(Event event) {

        if (display.getText().length() > 0) {  // поменять название кнопки
            reset.setText("C");
        }

        if (display.getText().equals("0")) { // сброс дефолтного значения
            display.setText("");
        }

        if (nextNumber) {   //проверка на начало следующего числа
            display.setText("");
            nextNumber = false;
        }

        Button btn = (Button) event.getSource();
        switch (btn.getId()) {
            case "zero":
                display.appendText("0");
                break;
            case "one":
                display.appendText("1");
                break;
            case "two":
                display.appendText("2");
                break;
            case "three":
                display.appendText("3");
                break;
            case "four":
                display.appendText("4");
                break;
            case "five":
                display.appendText("5");
                break;
            case "six":
                display.appendText("6");
                break;
            case "seven":
                display.appendText("7");
                break;
            case "eight":
                display.appendText("8");
                break;
            case "nine":
                display.appendText("9");
                break;
            case "comma":
                display.appendText(".");
                break;
        }
    }

    //обработчик операция
    @FXML
    public void handlerOperation(Event event) {
        Button btn = (Button) event.getSource();
        String operatorValue = btn.getId();

        if (!"equals".equals(operatorValue)) { // все операторы
            this.operator = operatorValue;
            this.number = Integer.parseInt(this.display.getText());
            this.nextNumber = true;
        } else {
            if (this.operator.isEmpty()) {
                return;
            }
            int numberTwo = Integer.parseInt(this.display.getText());
            int calculate = calculator.calculate(this.operator, this.number, numberTwo);
            this.display.setText(String.valueOf(calculate));
        }
    }

    @FXML
    public void handlerReset(Event event) {
        Button btn = (Button) event.getSource();
        String operatorValue = btn.getId();
        if (operatorValue.equals("reset")) {
            resetValueDisplay();
            this.operator = "";
            btn.setText("AC");
        }
    }


    @FXML
    public void handlerSing(Event event) {
        Button btn = (Button) event.getSource();
        String operatorValue = btn.getId();

        if (operatorValue.equals("sign")) {
            String number = display.getText();
            if (!number.equals(START_CURSOR_POSITION)) {
                if (number.startsWith("-")) {
                    display.setText(number.substring(0, 0) + number.substring(1));
                } else {
                    display.setText('-' + number);
                }
            }
        }
    }

    // >>>>>>>>> дополнительные методы

    // курсор калькулятора
    private static final String START_CURSOR_POSITION = "0";

//    // минимальный шрифт текста в дисплее
//    private static final int MIX_FONT_SIZE_TEXT = 10; // FONT TextField

    // максимальный шрифт текста в дисплее
    private static final int MAX_FONT_SIZE_TEXT = 32;  // FONT TextField

//    // Шаг уменьшения шрифта
//    private static final int STEP_FONT = 2;
//
//    private static int fontSize = 30; // на этапе инициализации
//
//    private static int length = 7; // на этапе инициализации
//
//    // обображение теста на дисплее в соотвествии с длинной строки
//    private void screenResizingTextField(TextField display) {
//        if (display.getText().length() > length && fontSize > MIX_FONT_SIZE_TEXT) {
//            display.setFont(new Font(fontSize));
//            length = length + 1;
//            fontSize = fontSize - STEP_FONT;
//        }
//    }

    // сбросить позицию курсора на 0
    private void resetValueDisplay() {
        this.display.setFont(new Font(MAX_FONT_SIZE_TEXT)); //reset FONT_SIZE
//        length = 7;
//        fontSize = 30;
        this.display.setText("0"); // set -> "0"
    }

}

