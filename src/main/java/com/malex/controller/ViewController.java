package com.malex.controller;

import com.malex.exception.impl.IncorrectDataException;
import com.malex.exception.impl.NoSuchOperationException;
import com.malex.exception.impl.UndefinedNumberException;
import com.malex.model.Calculator;
import com.malex.service.impl.*;
import javafx.animation.PauseTransition;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;
import javafx.util.Duration;
import org.apache.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ViewController {

    // инициализация логера
    private static Logger logger = Logger.getLogger(ViewController.class.getName());

    // курсор калькулятора
    private static final String START_CURSOR_POSITION = "0";

    // максимальный шрифт текста в дисплее
    private static final int MAX_FONT_SIZE_TEXT = 32;

    // дефолтое значение при получении процента одного числа
    private final static String PERCENT_NUMBER_DEFAULT = "1";

    // дефолтое значение при получении процента одного числа
    private final static String MEMORY_NUMBER_DEFAULT = "0";

    // дефолтое значение при нажатии на '.'
    private final static String PRESS_COMMA = "0.";

    // дефолтое значение при нажатии на '.'
    private final static String COMMA_VAL = ".";

    // дефолтое значение знака: -
    private static final String SIGN_VAL = "-";

    // название кнопки АС
    private static final String VALUE_BUTTON_RESET_AC = "AC";

    // индификатор кнопки '0' представления layout.fxml
    private static final String ID_ZERO = "zero";
    private static final String ID_ONE = "one";
    private static final String ID_TWO = "two";
    private static final String ID_THREE = "three";
    private static final String ID_FOUR = "four";
    private static final String ID_FIVE = "five";
    private static final String ID_SIX = "six";
    private static final String ID_SEVEN = "seven";
    private static final String ID_EIGHT = "eight";
    private static final String ID_NINE = "nine";
    private static final String ID_ADD = "add";
    private static final String ID_SUBTRACTION = "subtraction";
    private static final String ID_MULTIPLICATION = "multiplication";
    private static final String ID_DIVISION = "division";
    private static final String ID_PERCENT = "percent";
    private static final String ID_SIGN = "sign";
    private static final String ID_COMMA = "comma";
    private static final String ID_EQUALS = "equals";
    private static final String ID_RESET = "reset";

    @FXML
    private TextField display;

    @FXML
    private Button reset;

    @FXML
    private Button one;

    // значение первого числа
    private String numberOne = "";

    // значение второго числа
    private String numberTwo = "";

    // номер хранимый в памяти использую операции: MR; MC; M+; M-.
    private String numberInMemory = "0";

    // значение оператора
    private String operator = "";

    // индикация для следующего числа
    private boolean nextNumber;

    // индикация инициализации стартовой позиции курсора
    private boolean startPosition = true;

    // Переменная хранит состаяние повторного использования опереторов: +, -, *, /.
    private boolean replaceOperator = false;

    // индикация приоритетной арифметической операции
    private boolean isPriorityOperations = false;

    // переменная хранит арифметический приоритетный опретор
    private String operatorInMemory = "";

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

    // проверка являиться ли входящая сторка числом
    private boolean isNumber(String number) {
        Pattern pattern = Pattern.compile("[0-9|.|-]+");
        Matcher matcher = pattern.matcher(number);
        return matcher.matches();
    }


    // Обработчик кнопок
    @FXML
    public void handlerNumbersButton(Event event) {
        try {
            String value = display.getText();
            changeDisplaySize();
            if (value == null) {
                throw new IncorrectDataException("Incorrect data received from the controller !!!");
            }
            if (!isNumber(value)) {  // проверка входящего значения на число
                this.display.setText("");
                this.numberOne = "";
            }

            if (value.length() > 0) {  // поменять название кнопки
                reset.setText("C");
            }
            if (value.equals(START_CURSOR_POSITION) && startPosition) { // сброс дефолтного значения
                display.setText("");
                startPosition = false; // сброс стартовой позиции
            }
            if (value.startsWith(START_CURSOR_POSITION) && display.getText().length() == 1) { // проверка на не допущения 012345
                display.setText("");
            }
            if (nextNumber) {   //проверка на начало следующего числа
                display.setText("");
                nextNumber = false;
            }
            this.replaceOperator = false; // сброс повтора оператора при введении числа
            Button btn = (Button) event.getSource();
            switch (btn.getId()) {
                case ID_ZERO:
                    if (validateCountZero()) {
                        display.appendText(START_CURSOR_POSITION);
                    }
                    break;
                case ID_ONE:
                    display.appendText("1");
                    break;
                case ID_TWO:
                    display.appendText("2");
                    break;
                case ID_THREE:
                    display.appendText("3");
                    break;
                case ID_FOUR:
                    display.appendText("4");
                    break;
                case ID_FIVE:
                    display.appendText("5");
                    break;
                case ID_SIX:
                    display.appendText("6");
                    break;
                case ID_SEVEN:
                    display.appendText("7");
                    break;
                case ID_EIGHT:
                    display.appendText("8");
                    break;
                case ID_NINE:
                    display.appendText("9");
                    break;
                case ID_COMMA:
                    if (this.display.getText().isEmpty()) {  // если первое значение пустое 0.00000
                        this.display.appendText(PRESS_COMMA);
                    }
                    if (!this.display.getText().contains(COMMA_VAL)) {  // Проверка на наличее только одной точки
                        this.display.appendText(COMMA_VAL);
                    }
                    break;
                default:
                    break;
            }
        } catch (IncorrectDataException e) {
            logger.warn("IncorrectDataException -> handlerNumbersButton(Event event): " + e.getMessage());
        } catch (Exception e) {
            logger.error("Exception -> handlerNumbersButton(Event event): " + e.getMessage());
        }
    }

    // узнать приоритет операции
    private boolean isHighPriorityOperation(String operation) {
        return operation.equals(ID_MULTIPLICATION) || operation.equals(ID_DIVISION);
    }

    // Узнать приоритет операций
    // return true if displayOperation = '/' or '*' and inMemoryOperation = '+' or '-'
    private boolean getPriorityOperations(String displayOperation, String inMemoryOperation) {
        return isHighPriorityOperation(displayOperation) && !isHighPriorityOperation(inMemoryOperation);
    }

    //обработчик операций: =; +; -; *; /.
    @FXML
    public void handlerOperationButton(Event event) {
        Button btn = (Button) event.getSource();
        String operatorValue = btn.getId();
        try {
            if (!ID_EQUALS.equals(operatorValue)) {

                if (this.replaceOperator) {    // проверка на исключения проведения операций при повторном использованиии
                    this.replaceOperator = true;
                    this.operator = operatorValue;
                    return;
                }

                if (!this.operator.isEmpty() && this.operatorInMemory.isEmpty()) {   // проверка на приоритет операций
                    if (getPriorityOperations(operatorValue, this.operator)) {
                        this.operatorInMemory = operatorValue;
                        this.isPriorityOperations = true;
                        this.numberTwo = this.display.getText();
                        this.nextNumber = true;
                        return;
                    }
                }

                if (!isPriorityOperations) {  // блок выполняеться при срабатывании приоритета операций
                    if (numberTwo.isEmpty() && operator.isEmpty()) {
                        this.operator = operatorValue;
                        this.numberOne = this.display.getText();
                        this.nextNumber = true;
                    } else {
                        this.numberOne = calculator.calculate(this.operator, this.numberOne, this.display.getText());
                        this.display.setText(this.numberOne);
                        this.operator = operatorValue;
                        this.nextNumber = true;
                    }
                } else {
                    this.numberTwo = calculator.calculate(this.operatorInMemory, this.numberTwo, this.display.getText());
                    this.display.setText(this.numberTwo);
                    if (isHighPriorityOperation(this.operatorInMemory)) {
                        this.operatorInMemory = operatorValue;
                        this.isPriorityOperations = true;
                    } else {
                        this.operatorInMemory = "";
                        this.numberOne = calculator.calculate(this.operator, this.numberOne, this.numberTwo);
                        this.operator = operatorValue;
                        this.numberTwo = "";
                        this.isPriorityOperations = false;
                    }
                    this.nextNumber = true;
                }
                this.replaceOperator = true;
            } else {
                if (this.operator.isEmpty()) {
                    String number = this.display.getText();
                    if (validateValueComma(number)) {
                        this.display.setText(START_CURSOR_POSITION);
                    }
                    return;
                }

                String tempNumber = this.display.getText();
                if (isPriorityOperations) {
                    this.numberTwo = calculator.calculate(this.operatorInMemory, this.numberTwo, tempNumber);
                }

                if (numberTwo.isEmpty()) {  // сброс чисел в памяти
                    numberTwo = this.display.getText();
                }

                if (this.numberOne.equals("")) {
                    this.numberOne = this.numberTwo;
                }

                String calculate = calculator.calculate(this.operator, this.numberOne, this.numberTwo);
                if (!this.numberOne.isEmpty()) {
                    this.numberOne = calculate;
                }

                if (isPriorityOperations) {
                    this.operator = this.operatorInMemory;
                    this.numberOne = tempNumber;
                    this.numberTwo = "";
                    this.operatorInMemory = "";
                    this.isPriorityOperations = false;
                }
                this.display.setText(calculate);
            }
        } catch (UndefinedNumberException e) {
            this.display.setText("Undefined");
            this.operator = operatorValue;
        } catch (NoSuchOperationException e) {
            logger.warn("NoSuchOperationException -> handlerOperationButton(Event event): " + e.getMessage());
        } catch (Exception e) {
            logger.error("Exception type: " + e.getClass().getSimpleName() + " -> handlerOperationButton(Event event): " + e.getMessage());
        }
    }

    // Обработчик кнопки сброс 'AC'
    @FXML
    public void handlerResetButton(Event event) {
        Button btn = (Button) event.getSource();
        String operatorValue = btn.getId();
        if (operatorValue.equals(ID_RESET)) {
            resetDisplay();
            this.operator = "";
            btn.setText(VALUE_BUTTON_RESET_AC);
            startPosition = true; // сброс стартовой позиции
            this.numberOne = "";  // сброс первого числа
            this.numberTwo = "";  // сброс второго чиса
            isPriorityOperations = false; // сброс приоритета операторов
        }
    }

    //обработчик знака "-"
    @FXML
    public void handlerSingButton(Event event) {
        Button btn = (Button) event.getSource();
        String operatorValue = btn.getId();
        if (operatorValue.equals(ID_SIGN)) {
            String number = display.getText();
            if (!number.equals(START_CURSOR_POSITION)) {
                if (validateValueComma(number)) {
                    this.display.setText(START_CURSOR_POSITION);
                } else {
                    if (number.startsWith(SIGN_VAL)) {
                        this.display.setText(number.substring(0, 0) + number.substring(1));
                    } else {
                        this.display.setText(SIGN_VAL + number);
                    }
                }
            }
        }
    }

    // вычесление "%" чисел
    @FXML
    public void handlerPercentButton(Event event) {
        Button btn = (Button) event.getSource();
        String percent = btn.getId();
        if (numberOne.isEmpty()) {
            String calculate = calculator.calculate(percent, PERCENT_NUMBER_DEFAULT, this.display.getText());
            this.display.setText(calculate);
        } else {
            String calculate = calculator.calculate(percent, this.numberOne, this.display.getText());
            this.display.setText(calculate);
        }
    }

    // хранение переменной в памяти
    @FXML
    public void handlerMemoryButton(Event event) {
        Button btn = (Button) event.getSource();
        String memory = btn.getId();
        switch (memory) {
            case "mc":
                numberInMemory = MEMORY_NUMBER_DEFAULT;
                break;
            case "m_plus":
                numberInMemory = calculator.calculate(ID_ADD, numberInMemory, this.display.getText());
                this.nextNumber = true; //сброс начала курсора перед вводом нового числа
                break;
            case "m_minus":
                numberInMemory = calculator.calculate(ID_SUBTRACTION, numberInMemory, this.display.getText());
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
        return !display.getText().startsWith(START_CURSOR_POSITION) || display.getText().startsWith(PRESS_COMMA) || display.getText().isEmpty();
    }

    // Проверка на колличество допустимых '.' в числе
    private boolean validateValueComma(String number) {  //TODO rename method
        Pattern pattern = Pattern.compile("^0.[0]+"); // valid -> "0.0000"
        Matcher matcher = pattern.matcher(number);
        boolean checkValueZeroAndComma = number.startsWith(PRESS_COMMA) && number.length() == 2;
        return matcher.matches() || checkValueZeroAndComma;
    }

    // сбросить позицию курсора на 0
    private void resetDisplay() {
        this.display.setFont(new Font(MAX_FONT_SIZE_TEXT)); //reset FONT_SIZE
        this.display.setText(START_CURSOR_POSITION); // set -> "0"
    }

    // минимальный шрифт текста в дисплее
    private static final int MIN_FONT_SIZE_TEXT = 12;

    // change the display size
    private void changeDisplaySize() {
        this.display.textProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue.length() < 10) {  //TODO Constant
                        this.display.setFont(Font.font(MAX_FONT_SIZE_TEXT));
                    } else {
                        if (newValue.length() < 26) { //TODO Constant
                            this.display.setFont(Font.font(38 - newValue.length())); //TODO Constant
                        } else {
                            this.display.setFont(Font.font(MIN_FONT_SIZE_TEXT));
                        }
                    }
                }
        );
    }

    // Инициализация обработчика кнопок
    @FXML
    public void handlerKeyPressed(KeyEvent event) {
        KeyCode code = event.getCode();
        String nameKey = code.toString();
        System.out.println(nameKey);
        if (nameKey.equals("DIGIT1")) {
            one.arm();
            PauseTransition pause = new PauseTransition(Duration.seconds(0.2));
            pause.setOnFinished(e -> one.disarm());
            pause.play();
            one.fire();
        }

        switch (nameKey){
            case ID_ZERO:
                one.arm();
                PauseTransition pause = new PauseTransition(Duration.seconds(0.2));
                pause.setOnFinished(e -> one.disarm());
                pause.play();
                one.fire();
                break;
            case ID_ONE:

                break;
            case ID_TWO:

                break;
            case ID_THREE:

                break;
            case ID_FOUR:

                break;
            case ID_FIVE:

                break;
            case ID_SIX:

                break;
            case ID_SEVEN:

                break;
            case ID_EIGHT:

                break;
            case ID_NINE:

                break;
            case ID_COMMA:


                break;
            default:
                break;
        }
    }

}

