package com.malex.controller;

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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ViewController {

    // курсор калькулятора
    private static final String START_CURSOR_POSITION = "0";

    // максимальный шрифт текста в дисплее
    private static final int MAX_FONT_SIZE_TEXT = 32;  // FONT TextField

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

    // значение первого числа
    private String numberOne = "";

    // значение второго числа
    private String numberTwo = "";

    // значение оператора
    private String operator = "";

    // индикация для следующего числа
    private boolean nextNumber;

    // индикация инициализации стартовой позиции курсора
    private boolean startPosition = true;

    // Модель
    private static Calculator calculator;

    // Инициализация модели
    static {
        calculator = new Calculator();
        calculator.addOperation(new AddOperation());
        calculator.addOperation(new DivisionOperation());
        calculator.addOperation(new SubtractionOperation());
        calculator.addOperation(new MultiplicationOperation());
    }

    // Инициализация котроллера
    public void init() {
        this.display.setEditable(false);
        this.display.setText(START_CURSOR_POSITION);
    }

    // Обработчик кнопок
    @FXML
    public void handlerNumbers(Event event) {

        if (display.getText().length() > 0) {  // поменять название кнопки
            reset.setText("C");
        }

        if (display.getText().equals("0") && startPosition) { // сброс дефолтного значения
            display.setText("");
            startPosition = false; // сброс стартовой позиции
        }

        if (nextNumber) {   //проверка на начало следующего числа
            display.setText("");
            nextNumber = false;
        }

        Button btn = (Button) event.getSource();
        switch (btn.getId()) {
            case "zero":
                if (validateCountZero()) {
                    display.appendText("0");
                }
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
                if (this.display.getText().isEmpty()) {  // если первое значение пустое 0.00000
                    this.display.appendText("0.");
                }
                if (!this.display.getText().contains(".")) {    // Проверка на наличее только одной точки
                    this.display.appendText(".");
                }
                break;
            default:
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
            this.numberOne = this.display.getText();
            this.nextNumber = true;
        } else {
            if (this.operator.isEmpty()) {
                String number = this.display.getText();
                if (checkValueToZero(number)) {
                    this.display.setText("0");
                }
                return;
            }
            if (numberTwo.isEmpty()) {
                numberTwo = this.display.getText(); // сброс чисел в памяти
            }
            String calculate = calculator.calculate(this.operator, this.numberOne, numberTwo);
            if (!this.numberOne.isEmpty()) {
                this.numberOne = calculate;
            }
            this.display.setText(calculate);
        }
    }

    // Обработчик кнопки сброс
    @FXML
    public void handlerReset(Event event) {
        Button btn = (Button) event.getSource();
        String operatorValue = btn.getId();
        if (operatorValue.equals("reset")) {
            resetValueDisplay();
            this.operator = "";
            btn.setText("AC");
            startPosition = true; // сброс стартовой позиции
            this.numberOne = "";  // сброс первого числа
            this.numberTwo = "";  // сброс второго чиса
        }
    }

    //обработчик знака "-"
    @FXML
    public void handlerSing(Event event) {
        Button btn = (Button) event.getSource();
        String operatorValue = btn.getId();
        if (operatorValue.equals("sign")) {
            String number = display.getText();
            if (!number.equals(START_CURSOR_POSITION)) {
                if (checkValueToZero(number)) {
                    this.display.setText("0");
                } else {
                    if (number.startsWith("-")) {
                        this.display.setText(number.substring(0, 0) + number.substring(1));
                    } else {
                        this.display.setText('-' + number);
                    }
                }
            }
        }
    }

    // Проверка на количество допустимых нулей в числе
    private boolean validateCountZero() {
        return !display.getText().startsWith("0") || display.getText().startsWith("0.") || display.getText().isEmpty();
    }

    // Проверка на колличество допустимых '.' в числе
    private boolean checkValueToZero(String number) {
        Pattern pattern = Pattern.compile("^0.[0]+");
        Matcher matcher = pattern.matcher(number); // valid -> "0.0000"
        boolean checkValueZeroAndComma = number.startsWith("0.") && number.length() == 2;
        return matcher.matches() || checkValueZeroAndComma;
    }

    // сбросить позицию курсора на 0
    private void resetValueDisplay() {
        this.display.setFont(new Font(MAX_FONT_SIZE_TEXT)); //reset FONT_SIZE
//        length = 7;
//        fontSize = 30;
        this.display.setText("0"); // set -> "0"
    }

//    // минимальный шрифт текста в дисплее
//    private static final int MIX_FONT_SIZE_TEXT = 10; // FONT TextField


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
}

