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

/**
 * The {@code ViewController} class translate interactions with the view {@code layout.fxml}  into actions to be performed of the model {@code Calculator}.
 * <p>
 * Here are some more examples of how this can be used:
 * <pre>
 * // Step #1: Load fxml file.
 * FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/view/layout.fxml"));
 * Pane pane = loader.load();
 *
 * // Step #2: Get the object of class {@code ViewController}.
 * ViewController controller = loader.getController();
 *
 * // Step #3:  Initialization the controller {@code ViewController}.
 * controller.init();
 * </pre>
 *
 * @author MAlex
 * @see com.malex.model.Calculator
 * @see java.lang.String
 * @see javafx.fxml.FXML
 * @see javafx.scene.control.Button
 * @see javafx.scene.control.TextField
 * @see javafx.scene.input.KeyCode
 * @see javafx.scene.text.Font
 * @see javafx.util.Duration
 * @see org.apache.log4j.Logger
 * @see java.util.regex.Matcher
 * @see java.util.regex.Pattern
 */
public class ViewController {

    /**
     * Logging initialization.
     */
    private static Logger logger = Logger.getLogger(ViewController.class.getName());

    /**
     * Value is used to store the cursor.
     */
    private static final String START_CURSOR_POSITION = "0";

    /**
     * Value is used to store the maximum font size a text.
     */
    private static final int MAX_FONT_SIZE_TEXT = 32;

    /**
     * Value is used to store the minimum font size a text.
     */
    private static final int MIN_FONT_SIZE_TEXT = 12;

    /**
     * Value is used to store the value the default percent of number '1'.
     */
    private final static String PERCENT_NUMBER_DEFAULT = "1";

    /**
     * Value is used to store the value '0.'.
     */
    private final static String PRESS_COMMA = "0.";


    /**
     * Value is used to store the value '.'.
     */
    private final static String COMMA_VAL = ".";

    /**
     * Value is used to store the value '-'.
     */
    private static final String SIGN_VAL = "-";

    /**
     * Value is used to store the value 'AC'.
     */
    private static final String VALUE_BUTTON_RESET_AC = "AC";

    /**
     * Value is used to store the indicator of a buttons.
     */
    private static final String ID_ZERO = "DIGIT0";
    private static final String ID_ONE = "DIGIT1";
    private static final String ID_TWO = "DIGIT2";
    private static final String ID_THREE = "DIGIT3";
    private static final String ID_FOUR = "DIGIT4";
    private static final String ID_FIVE = "DIGIT5";
    private static final String ID_SIX = "DIGIT6";
    private static final String ID_SEVEN = "DIGIT7";
    private static final String ID_EIGHT = "DIGIT8";
    private static final String ID_NINE = "DIGIT9";
    private static final String ID_ADD = "ADD";
    private static final String ID_SUBTRACTION = "SUBTRACTION";
    private static final String ID_MULTIPLICATION = "MULTIPLICATION";
    private static final String ID_DIVISION = "DIVISION";
    private static final String ID_PERCENT = "PERCENT";
    private static final String ID_SIGN = "SIGN";
    private static final String ID_COMMA = "COMMA";
    private static final String ID_EQUALS = "EQUALS";
    private static final String ID_RESET = "ESCAPE";

    // индификаторы кнопок представления layout.fxml
    @FXML
    private TextField display;
    @FXML
    public Button DIGIT0;
    @FXML
    public Button DIGIT1;
    @FXML
    public Button DIGIT2;
    @FXML
    public Button DIGIT3;
    @FXML
    public Button DIGIT4;
    @FXML
    public Button DIGIT5;
    @FXML
    public Button DIGIT6;
    @FXML
    public Button DIGIT7;
    @FXML
    public Button DIGIT8;
    @FXML
    public Button DIGIT9;
    @FXML
    public Button COMMA;
    @FXML
    public Button MULTIPLICATION;
    @FXML
    public Button DIVISION;
    @FXML
    public Button ADD;
    @FXML
    public Button SUBTRACTION;
    @FXML
    public Button EQUALS;
    @FXML
    public Button ESCAPE;
    @FXML
    public Button SIGN;
    @FXML
    public Button PERCENT;

    /**
     * Value is used to store the first number.
     */
    private String numberOne = "";

    /**
     * Value is used to store the second number.
     */
    private String numberTwo = "";

    /**
     * Value is used to store the arithmetic operator in memory for an operation: MR; MC; M+; M-.
     */
    private String numberInMemory = "0";

    /**
     * Value is used to store the arithmetic operator in memory.
     */
    private String operator = "";

    /**
     * Value is used to indicate next a number.
     */
    private boolean nextNumber;

    /**
     * Value is used to indicate starting position of a cursor.
     */
    private boolean startPosition = true;

    /**
     * Value is used to store the status of reuse operators
     */
    private boolean replaceOperator = false;

    /**
     * Value is used to indicate the priority operation.
     */
    private boolean isPriorityOperations = false;

    /**
     * Value is used to store the priority an operator in memory.
     */
    private String operatorInMemory = "";

    /**
     * Value is used to store model  {@code Calculator}.
     */
    private static Calculator calculator;

    /**
     * Initialization the model of a calculator.
     */
    static {
        calculator = new Calculator();
        calculator.addOperation(new AddOperation());
        calculator.addOperation(new DivisionOperation());
        calculator.addOperation(new SubtractionOperation());
        calculator.addOperation(new MultiplicationOperation());
        calculator.addOperation(new PercentOperation());
    }

    /**
     * Initialization the controller.
     */
    public void init() {
        this.display.setEditable(false);
        this.display.setText(START_CURSOR_POSITION);
    }

    /**
     * Handler pressing on click the buttons: '1', '2', '3', '4', '5', '6', '7', '8', '9', '0'.
     *
     * @param event this event is generated when a buttons is pressed.
     */
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
                ESCAPE.setText("C");
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
                    if (validateLimitNumberZeros()) {
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

    /**
     * Handler pressing on click the buttons: '=', '+', '-', '*', '/'.
     *
     * @param event this event is generated when a buttons '=', '+', '-', '*', '/' is pressed.
     */
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
                    if (validateNumberAvailableInsideComma(number)) {
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
                System.out.println();
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

    /**
     * Handler pressing on click the button: 'AC'.
     *
     * @param event this event is generated when a button 'AC' is pressed.
     */
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

    /**
     * Handler pressing on click the button: '-'.
     *
     * @param event this event is generated when a button '-' is pressed.
     */
    @FXML
    public void handlerSingButton(Event event) {
        Button btn = (Button) event.getSource();
        String operatorValue = btn.getId();
        if (operatorValue.equals(ID_SIGN)) {
            String number = display.getText();
            if (!number.equals(START_CURSOR_POSITION)) {
                if (validateNumberAvailableInsideComma(number)) {
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

    /**
     * Handler pressing on click the button: '%'.
     *
     * @param event this event is generated when a button '%' is pressed.
     */
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

    /**
     * Handler pressing on click the buttons: 'mr', 'mc','m-','m+'.
     *
     * @param event this event is generated when a buttons 'mr', 'mc','m-','m+' is pressed.
     */
    @FXML
    public void handlerMemoryButton(Event event) {
        Button btn = (Button) event.getSource();
        String memory = btn.getId();
        switch (memory) {
            case "mc":
                numberInMemory = START_CURSOR_POSITION;
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

    /**
     * Initialize keyboard input data handler.
     *
     * @param event this event is generated when a key is pressed, released, or typed.
     */
    @FXML
    public void handlerKeyPressed(KeyEvent event) {
        String nameKey = shortcuts(event);
        PauseTransition pause = new PauseTransition(Duration.seconds(0.1));
        switch (nameKey) {
            case ID_ZERO:
                clickOnButton(DIGIT0, pause);
                break;
            case ID_ONE:
                clickOnButton(DIGIT1, pause);
                break;
            case ID_TWO:
                clickOnButton(DIGIT2, pause);
                break;
            case ID_THREE:
                clickOnButton(DIGIT3, pause);
                break;
            case ID_FOUR:
                clickOnButton(DIGIT4, pause);
                break;
            case ID_FIVE:
                clickOnButton(DIGIT5, pause);
                break;
            case ID_SIX:
                clickOnButton(DIGIT6, pause);
                break;
            case ID_SEVEN:
                clickOnButton(DIGIT7, pause);
                break;
            case ID_EIGHT:
                clickOnButton(DIGIT8, pause);
                break;
            case ID_NINE:
                clickOnButton(DIGIT9, pause);
                break;
            case ID_COMMA:
                clickOnButton(COMMA, pause);
                break;
            case ID_MULTIPLICATION:
                clickOnButton(MULTIPLICATION, pause);
                break;
            case ID_DIVISION:
                clickOnButton(DIVISION, pause);
                break;
            case ID_SUBTRACTION:
                clickOnButton(SUBTRACTION, pause);
                break;
            case ID_ADD:
                clickOnButton(ADD, pause);
                break;
            case ID_SIGN:
                clickOnButton(SIGN, pause);
                break;
            case ID_PERCENT:
                clickOnButton(PERCENT, pause);
                break;
            case ID_EQUALS:
                clickOnButton(EQUALS, pause);
                break;
            case ID_RESET:
                clickOnButton(ESCAPE, pause);
                break;
            default:
                break;
        }
    }

    /**
     * Change the display size.
     */
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

    /**
     * Click on a given button.
     *
     * @param button the button.
     * @param pause  the pause animation press on click button.
     */
    private void clickOnButton(Button button, PauseTransition pause) {
        button.fire();
        button.arm();
        pause.setOnFinished(e -> button.disarm());
        pause.play();
    }

    /**
     * Get priority operations.
     * This method compares two operations.
     *
     * @param displayOperation  the operation selected by the user.
     * @param inMemoryOperation the operation in memory.
     * @return true if the operation in display has a higher priority.
     */
    // return true if displayOperation = '/' or '*' and inMemoryOperation = '+' or '-'
    private boolean getPriorityOperations(String displayOperation, String inMemoryOperation) {
        return isHighPriorityOperation(displayOperation) && !isHighPriorityOperation(inMemoryOperation);
    }

    /**
     * Get priority operation.
     *
     * @param operation the arithmetic operation
     * @return true if the operation has a higher priority.
     */
    private boolean isHighPriorityOperation(String operation) {
        return operation.equals(ID_MULTIPLICATION) || operation.equals(ID_DIVISION);
    }

    /**
     * Verification whether a string is a number.
     *
     * @param number the incoming parameter.
     * @return true if number.
     */
    private boolean isNumber(String number) {
        Pattern pattern = Pattern.compile("[0-9|.|-]+");
        Matcher matcher = pattern.matcher(number);
        return matcher.matches();
    }

    /**
     * Validate the number on the limit count of zeros.
     *
     * @return true if the count of zeros in the permitted limit.
     */
    private boolean validateLimitNumberZeros() {
        return !display.getText().startsWith(START_CURSOR_POSITION) || display.getText().startsWith(PRESS_COMMA) || display.getText().isEmpty();
    }


    /**
     * Validate the number on available inside the comma.
     *
     * @param number the incoming parameter.
     * @return true if the count of commas in the permitted limit.
     */
    private boolean validateNumberAvailableInsideComma(String number) {
        Pattern pattern = Pattern.compile("^0.[0]+");
        Matcher matcher = pattern.matcher(number);
        boolean checkValueZeroAndComma = number.startsWith(PRESS_COMMA) && number.length() == 2;
        return matcher.matches() || checkValueZeroAndComma;
    }

    /**
     * Reset the cursor position of the screen.
     */
    private void resetDisplay() {
        this.display.setFont(new Font(MAX_FONT_SIZE_TEXT));
        this.display.setText(START_CURSOR_POSITION);
    }

    /**
     * The method determines the Shortcuts.
     *
     * @param event this event is generated when a key is pressed, released, or typed.
     * @return the id button.
     */
    private String shortcuts(KeyEvent event) {
        KeyCode code = event.getCode();
        String nameKey = code.toString();

        if (event.getText().equals("*") && nameKey.equals("DIGIT8")) { //TODO Constant
            return ID_MULTIPLICATION;
        }
        if (event.getText().equals("+") && nameKey.equals("EQUALS")) {  //TODO Constant
            return ID_ADD;
        }
        if (event.getText().equals("_") && nameKey.equals("MINUS")) {   //TODO Constant
            return ID_SIGN;
        }
        if (event.getText().equals("-") && nameKey.equals("MINUS")) {   //TODO Constant
            return ID_SUBTRACTION;
        }
        if (event.getText().equals("%") && nameKey.equals("DIGIT5")) {   //TODO Constant
            return ID_PERCENT;
        }
        if (nameKey.equals("SLASH")) {   //TODO Constant
            return ID_DIVISION;
        }
        if (nameKey.equals("ENTER")) {   //TODO Constant
            return ID_EQUALS;
        }
        return nameKey;
    }
}

