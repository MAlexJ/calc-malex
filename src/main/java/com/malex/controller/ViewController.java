package com.malex.controller;

import com.malex.exception.impl.IncorrectDataException;
import com.malex.exception.impl.NoSuchOperationException;
import com.malex.exception.impl.UndefinedNumberException;
import com.malex.model.Calculator;
import com.malex.service.impl.*;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
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
    private final static String COMMA = ".";

    // дефолтое значение знака: -
    private static final String SIGN = "-";

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
    public void handlerNumbers(Event event) {
        try {
            String value = display.getText();
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
                    if (!this.display.getText().contains(COMMA)) {  // Проверка на наличее только одной точки
                        this.display.appendText(COMMA);
                    }
                    break;
                default:
                    break;
            }
        } catch (IncorrectDataException e) {
            logger.warn("IncorrectDataException -> handlerNumbers(Event event): " + e.getMessage());
        } catch (Exception e) {
            logger.error("Exception -> handlerNumbers(Event event): " + e.getMessage());
        }
    }

    // узнать приоритет операции
    private boolean isHighPriorityOperations(String operation) {
        return operation.equals(ID_MULTIPLICATION) || operation.equals(ID_DIVISION);
    }

    // Узнать приоритет операций
    // return true if displayOperation = '/' or '*' and inMemoryOperation = '+' or '-'
    private boolean getPriority(String displayOperation, String inMemoryOperation) {
        return isHighPriorityOperations(displayOperation) && !isHighPriorityOperations(inMemoryOperation);
    }

    //обработчик операций: =; +; -; *; /.
    @FXML
    public void handlerOperation(Event event) {
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
                    if (getPriority(operatorValue, this.operator)) {
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
                    if (isHighPriorityOperations(this.operatorInMemory)) {
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
                    if (checkValueToZero(number)) {
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
            logger.warn("NoSuchOperationException -> handlerOperation(Event event): " + e.getMessage());
        } catch (Exception e) {
            logger.error("Exception type: " + e.getClass().getSimpleName() + " -> handlerOperation(Event event): " + e.getMessage());
        }
    }

    // Обработчик кнопки сброс 'AC'
    @FXML
    public void handlerReset(Event event) {
        Button btn = (Button) event.getSource();
        String operatorValue = btn.getId();
        if (operatorValue.equals(ID_RESET)) {
            resetValueDisplay();
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
    public void handlerSing(Event event) {
        Button btn = (Button) event.getSource();
        String operatorValue = btn.getId();
        if (operatorValue.equals(ID_SIGN)) {
            String number = display.getText();
            if (!number.equals(START_CURSOR_POSITION)) {
                if (checkValueToZero(number)) {
                    this.display.setText(START_CURSOR_POSITION);
                } else {
                    if (number.startsWith(SIGN)) {
                        this.display.setText(number.substring(0, 0) + number.substring(1));
                    } else {
                        this.display.setText(SIGN + number);
                    }
                }
            }
        }
    }

    // вычесление "%" чисел
    @FXML
    public void handlerPercent(Event event) {
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
    public void handlerMemory(Event event) {
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
    private boolean checkValueToZero(String number) {
        Pattern pattern = Pattern.compile("^0.[0]+"); // valid -> "0.0000"
        Matcher matcher = pattern.matcher(number);
        boolean checkValueZeroAndComma = number.startsWith(PRESS_COMMA) && number.length() == 2;
        return matcher.matches() || checkValueZeroAndComma;
    }

    // сбросить позицию курсора на 0
    private void resetValueDisplay() {
        this.display.setFont(new Font(MAX_FONT_SIZE_TEXT)); //reset FONT_SIZE
        this.display.setText(START_CURSOR_POSITION); // set -> "0"
    }

//    // TODO Инициализация обработчика кнопок
//    @FXML
//    public void handlerKeyPressed(KeyEvent event) {
//
//        if (display.getText().length() > 0) {  // поменять название кнопки
//            reset.setText("C");
//        }
//
//        if (display.getText().equals(START_CURSOR_POSITION) && startPosition) { // сброс дефолтного значения
//            display.setText("");
//            startPosition = false; // сброс стартовой позиции
//        }
//
//        if (nextNumber) {   //проверка на начало следующего числа
//            display.setText("");
//            nextNumber = false;
//        }
//
//        switch (event.getText()) {
//            case "0":
//                if (validateCountZero()) {
//                    display.appendText(START_CURSOR_POSITION);
//                }
//                break;
//            case "1":
//                display.appendText("1");
//                break;
//            case "2":
//                display.appendText("2");
//                break;
//            case "3":
//                display.appendText("3");
//                break;
//            case "4":
//                display.appendText("4");
//                break;
//            case "5":
//                display.appendText("5");
//                break;
//            case "6":
//                display.appendText("6");
//                break;
//            case "7":
//                display.appendText("7");
//                break;
//            case "8":
//                display.appendText("8");
//                break;
//            case "9":
//                display.appendText("9");
//                break;
//            case ".":
//                if (display.getText().isEmpty()) {  // если первое значение пустое 0.00000
//                    display.appendText("0.");
//                }
//                if (!display.getText().contains(".")) {    // Проверка на наличее только одной точки
//                    display.appendText(".");
//                }
//                break;
//            default:
//                break;
//        }
//    }

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

