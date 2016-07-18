package com.malex.controller;

import com.malex.model.Calculator;
import com.malex.service.impl.*;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
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
    private Button reset;


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
        calculator.addOperation(new PercentOperation());
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

        if (display.getText().startsWith("0") && display.getText().length() == 1) { // проверка на не допущения 012345
            display.setText("");
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

    //обработчик операций: +; -; *; /.
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

    // Обработчик кнопки сброс 'AC'
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

    // дефолтое значение при получении процента одного числа
    private final static String PERCENT_NUMBER_DEFAULT = "1";

    //обработчик знака "%"
    @FXML
    public void handlerPercent(Event event) {
        Button btn = (Button) event.getSource();
        String percent = btn.getId();
        if (numberTwo.isEmpty()) {
            String calculate = calculator.calculate(percent, PERCENT_NUMBER_DEFAULT, this.display.getText());
            this.display.setText(calculate);
        } else {
            String calculate = calculator.calculate(percent, this.numberOne, this.display.getText());
            this.display.setText(calculate);
        }
    }

    // дефолтое значение при получении процента одного числа
    private final static String MEMORY_NUMBER_DEFAULT = "0";

    // номер хранимый в памяти использую операции: MR; MC; M+; M-.
    private static String numberInMemory = "0";

    @FXML
    public void handlerMemory(Event event) {
        Button btn = (Button) event.getSource();
        String memory = btn.getId();

        switch (memory) {
            case "mc":
                numberInMemory = MEMORY_NUMBER_DEFAULT;
                break;

            case "m_plus":
                numberInMemory = calculator.calculate("add", numberInMemory, this.display.getText());  //TODO "add" -> constant
                this.nextNumber = true; //сброс начала курсора перед вводом нового числа
                break;

            case "m_minus":
                numberInMemory = calculator.calculate("subtraction", numberInMemory, this.display.getText());  //TODO "subtraction" -> constant
                this.nextNumber = true;  //сброс начала курсора перед вводом нового числа
                break;

            case "mr":
                this.display.setText(numberInMemory);
                break;

            default:
                break;
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

    // TODO Инициализация обработчика кнопок
    @FXML
    private void handlerKeyPressed(KeyEvent event) {

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

        switch (event.getText()) {
            case "0":
                if (validateCountZero()) {
                    display.appendText("0");
                }
                break;
            case "1":
                display.appendText("1");
                break;
            case "2":
                display.appendText("2");
                break;
            case "3":
                display.appendText("3");
                break;
            case "4":
                display.appendText("4");
                break;
            case "5":
                display.appendText("5");
                break;
            case "6":
                display.appendText("6");
                break;
            case "7":
                display.appendText("7");
                break;
            case "8":
                display.appendText("8");
                break;
            case "9":
                display.appendText("9");
                break;
            case ".":
                if (display.getText().isEmpty()) {  // если первое значение пустое 0.00000
                    display.appendText("0.");
                }
                if (!display.getText().contains(".")) {    // Проверка на наличее только одной точки
                    display.appendText(".");
                }
                break;
            default:
                break;
        }
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

