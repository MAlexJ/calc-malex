package com.malex.controller;

import com.malex.model.enums.Operation;
import com.malex.model.exception.UndefinedNumberException;
import javafx.animation.PauseTransition;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static com.malex.model.Calculator.CALCULATOR;

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
 */
public class ViewController {

    /**
     * Logging initialization.
     */
    private static Logger logger = Logger.getLogger(ViewController.class.getName());

    /**
     * Minimum length of digits.
     */
    private static final int ALLOWABLE_MINIMUM_LENGTH_OF_DIGITS = 7;
    /**
     * Maximum length of digits.
     */
    private static final int MAXIMUM_LENGTH = 30;

    /**
     * Start position.
     */
    private static final String START_POSITION = "0";

    /**
     * Value is used to store the reset number. //TODO rename
     */
    private static final String RESET_NUMBER = "";

    /**
     * The maximum font size a text.
     */
    private static final int MAX_FONT_SIZE_TEXT = 46;

    /**
     * The pause animation.
     */
    private static final double PAUSE_ANIMATION = 0.1;

    /**
     * One hundredth part of number.
     */
    private static final String PERCENT_PART_NUMBER = "1";

    /**
     * The pattern of validate the number.
     */
    private final static String PATTERN_NUMBER = "[-+0-9|.E]+";

    /**
     * The pattern validation of the number of commas.
     */
    private final static String PATTERN_COMA = "^0.[0]+|(0.)??";

    /**
     * The style font size.
     */
    private static final String FX_FONT_SIZE = "-fx-font-size: ";

    /**
     * The font size.
     */
    private static final String FONT_SIZE = "px;";

    /**
     * The screen font.
     */
    private static final Font FONT_APP = new Font("Helvetica Neue Thin", MAX_FONT_SIZE_TEXT);           //todo move from here   >>> FIX

    /**
     * Value is used to store the default number in memory for an operation: MR; MC; M+; M-.
     */
    private static final BigDecimal DEFAULT_NUMBER = new BigDecimal(START_POSITION);

    /**
     * Value is used to store exponent. //TODO
     */
    private static final String EXPONENT_VAL = "E";
    /**
     * Value is used to store exponent.  //TODO
     */
    private static final String EXPONENT_PLUS_VAL = "E+";

    /**
     * The maximum number for engineering calculations.
     */
    private static final BigDecimal MAX_NOT_ENGINEERING_VALUE = new BigDecimal("9999999999999999");

    /**
     * The minimum number for engineering calculations.
     */
    private static final BigDecimal MIN_NOT_ENGINEERING_VALUE = new BigDecimal("-999999999999999");

    /**
     * The value '0.'.
     */
    private static final String ZERO_COMMA = "0.";

    /**
     * The value '.'.
     */
    private static final String COMMA_VALUE = ".";

    /**
     * The value '-'.
     */
    private static final String SIGN_VALUE = "-";

    /**
     * Mapping shortcut.
     */
    private static final Map<KeyCode, Button> SHORTCUT_MAP = new HashMap<>();

    /**
     * Mapping buttons.
     */
    private static final Map<KeyCode, Button> KEY_MAP = new HashMap<>();

    /**
     * Value is used to store ID.
     */
    @FXML
    public TextField DISPLAY;
    @FXML
    public Button MR;
    @FXML
    public Button MC;
    @FXML
    public Button M_PLUS;
    @FXML
    public Button M_MINUS;
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
    public Button ADDITION;
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
    @FXML
    public Button EXIT;
    @FXML
    public Button TRAY;

    /**
     * Position on the x-axis.
     */
    private static double POSITION_X;

    /**
     * Position on the y-axis.
     */
    private static double POSITION_Y;

    /**
     *The arithmetic operator in memory for an operation: MR; MC; M+; M-.
     */
    private static BigDecimal numberInMemory;

    /**
     * The first number.
     */
    private String numberOne = "";

    /**
     * The second number.
     */
    private String numberTwo = "";

    /**
     * The arithmetic operator in memory.
     */
    private String operator = "";   //todo review all varable names, convert String -> BigDecimal or Operator

    /**
     * The priority an operator in memory.
     */
    private String operatorInMemory = "";

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
    private boolean replaceOperator;

    /**
     * Value is used to store reuse operator 'equals'.
     */
    private boolean operatorEqualsUsed;

    /**
     * Value is used to indicate the priority operation.
     */
    private boolean isPriorityOperations;

    /**
     * Initialization the controller.
     */
    public void init() {
        DISPLAY.setEditable(false);
        DISPLAY.setFont(FONT_APP);
        DISPLAY.setText(START_POSITION);
        DISPLAY.lengthProperty().addListener((observable, oldValue, newValue) -> {
            changeDisplaySize(newValue.intValue());
        });
    }

    /**
     * Initialization the mapping of buttons.
     */
    @FXML
    void initialize() {
        SHORTCUT_MAP.put(KeyCode.EQUALS, ADDITION);
        SHORTCUT_MAP.put(KeyCode.DIGIT8, MULTIPLICATION);
        SHORTCUT_MAP.put(KeyCode.MINUS, SIGN);
        SHORTCUT_MAP.put(KeyCode.DIGIT5, PERCENT);

        KEY_MAP.put(KeyCode.DIGIT0, DIGIT0);
        KEY_MAP.put(KeyCode.DIGIT1, DIGIT1);
        KEY_MAP.put(KeyCode.DIGIT2, DIGIT2);
        KEY_MAP.put(KeyCode.DIGIT3, DIGIT3);
        KEY_MAP.put(KeyCode.DIGIT4, DIGIT4);
        KEY_MAP.put(KeyCode.DIGIT5, DIGIT5);
        KEY_MAP.put(KeyCode.DIGIT6, DIGIT6);
        KEY_MAP.put(KeyCode.DIGIT7, DIGIT7);
        KEY_MAP.put(KeyCode.DIGIT8, DIGIT8);
        KEY_MAP.put(KeyCode.DIGIT9, DIGIT9);

        KEY_MAP.put(KeyCode.COMMA, COMMA);
        KEY_MAP.put(KeyCode.SLASH, DIVISION);
        KEY_MAP.put(KeyCode.MINUS, SUBTRACTION);
        KEY_MAP.put(KeyCode.EQUALS, EQUALS);
        KEY_MAP.put(KeyCode.ENTER, EQUALS);
        KEY_MAP.put(KeyCode.ESCAPE, ESCAPE);
        KEY_MAP.put(KeyCode.C, MC);
        KEY_MAP.put(KeyCode.R, MR);
        KEY_MAP.put(KeyCode.M, M_MINUS);
        KEY_MAP.put(KeyCode.P, M_PLUS);
    }

    /**
     * Handler pressing on click the buttons: '1', '2', '3', '4', '5', '6', '7', '8', '9', '0'.
     *
     * @param event this event is generated when a buttons is pressed.
     */
    @FXML
    public void handlerNumbersButton(Event event) {
        String textDisplay = DISPLAY.getText();
        if (!isNumber(textDisplay)) {
            textDisplay = RESET_NUMBER;
            numberOne = RESET_NUMBER;
        } else {
            if (textDisplay.equals(START_POSITION) && startPosition) {
                textDisplay = RESET_NUMBER;
                startPosition = false;
            } else if (textDisplay.startsWith(START_POSITION) && textDisplay.length() == 1) {
                textDisplay = RESET_NUMBER;
            }
        }
        if (nextNumber) {
            textDisplay = RESET_NUMBER;
            nextNumber = false;
        }
        replaceOperator = false;

        if (textDisplay.length() < MAXIMUM_LENGTH) {
            Button btn = (Button) event.getSource();

            int number = -1;                                     //todo int     >>> FIX
            String coma = RESET_NUMBER;

            if (btn.equals(DIGIT0)) {
                if (validateLimitNumberZeros(textDisplay)) {
                    number = 0;
                }
            } else if (btn.equals(DIGIT1)) {
                number = 1;
            } else if (btn.equals(DIGIT2)) {
                number = 2;
            } else if (btn.equals(DIGIT3)) {
                number = 3;
            } else if (btn.equals(DIGIT4)) {
                number = 4;
            } else if (btn.equals(DIGIT5)) {
                number = 5;
            } else if (btn.equals(DIGIT6)) {
                number = 6;
            } else if (btn.equals(DIGIT7)) {
                number = 7;
            } else if (btn.equals(DIGIT8)) {
                number = 8;
            } else if (btn.equals(DIGIT9)) {
                number = 9;
            } else if (btn.equals(COMMA)) {
                if (textDisplay.isEmpty()) {
                    coma = ZERO_COMMA;
                } else if (!textDisplay.contains(COMMA_VALUE)) {
                    coma = COMMA_VALUE;
                }
            }

            if (number != -1) {
                textDisplay = textDisplay + number;
            }

            DISPLAY.setText(textDisplay + coma);
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
        String textDisplay = DISPLAY.getText();
        try {
            if (!EQUALS.getId().equals(operatorValue)) {
                if (operatorEqualsUsed) {
                    numberOne = textDisplay;
                    operator = operatorValue;
                    numberTwo = RESET_NUMBER;
                    nextNumber = true;
                    operatorEqualsUsed = false;
                } else {
                    if (replaceOperator) {
                        replaceOperator = true;
                        operator = operatorValue;
                    } else {
                        if (!operator.isEmpty() && operatorInMemory.isEmpty()) {
                            if (getPriorityOperations(operatorValue, operator)) {
                                operatorInMemory = operatorValue;
                                isPriorityOperations = true;
                                numberTwo = textDisplay;
                                nextNumber = true;
                                return;
                            }
                        }
                        if (!isPriorityOperations) {
                            if (numberTwo.isEmpty() && operator.isEmpty()) {
                                operator = operatorValue;
                                numberOne = textDisplay;
                                nextNumber = true;
                            } else {
                                numberOne = calculate(operator, numberOne, textDisplay);
                                operator = operatorValue;
                                nextNumber = true;
                                DISPLAY.setText(numberOne);
                            }
                        } else {
                            numberTwo = calculate(operatorInMemory, numberTwo, textDisplay);
                            DISPLAY.setText(numberTwo);
                            if (isHighPriorityOperation(operatorInMemory)) {
                                operatorInMemory = operatorValue;
                                isPriorityOperations = true;
                            } else {
                                operatorInMemory = RESET_NUMBER;
                                numberOne = calculate(operator, numberOne, numberTwo);
                                operator = operatorValue;
                                numberTwo = RESET_NUMBER;
                                isPriorityOperations = false;
                            }
                            nextNumber = true;
                        }
                        replaceOperator = true;
                    }
                }
            } else {
                if (!operator.isEmpty()) {
                    if (isPriorityOperations) {
                        numberTwo = calculate(operatorInMemory, numberTwo, textDisplay);
                    }
                    if (numberTwo.isEmpty()) {
                        numberTwo = DISPLAY.getText();
                    }
                    if (numberOne.equals(RESET_NUMBER)) {
                        numberOne = numberTwo;
                    }
                    if (!operator.equals(EQUALS.getId())) {
                        String calculate = calculate(operator, numberOne, numberTwo);
                        operatorEqualsUsed = true;
                        if (!numberOne.isEmpty()) {
                            numberOne = calculate;
                        }
                        if (isPriorityOperations) {
                            operator = operatorInMemory;
                            numberOne = textDisplay;
                            numberTwo = RESET_NUMBER;
                            operatorInMemory = RESET_NUMBER;
                            isPriorityOperations = false;
                        }
                        textDisplay = calculate;
                    }
                } else {
                    if (validateNumberAvailableInsideComma(textDisplay)) {
                        textDisplay = START_POSITION;
                    }
                }
                DISPLAY.setText(textDisplay);
            }
        } catch (UndefinedNumberException e) {
            DISPLAY.setText("Undefined");
            operator = operatorValue;
        } catch (RuntimeException e) {
            logger.error("Exception type: " + e.getClass().getSimpleName() + " -> handlerOperationButton(Event event): " + e.getMessage());
        }
    }

    /**
     * Handler pressing on click the button: 'AC'.
     */
    @FXML
    public void handlerResetButton() {
        operator = RESET_NUMBER;
        numberOne = RESET_NUMBER;
        numberTwo = RESET_NUMBER;
        startPosition = true;
        isPriorityOperations = false;
        operatorEqualsUsed = false;
        DISPLAY.setStyle(FX_FONT_SIZE + MAX_FONT_SIZE_TEXT + FONT_SIZE);
        DISPLAY.setText(START_POSITION);
    }

    /**
     * Handler pressing on click the button: '-'.
     */
    @FXML
    public void handlerSingButton() {
        String textDisplay = DISPLAY.getText();
        if (!textDisplay.equals(START_POSITION)) {
            String newTextDisplay;

            if (validateNumberAvailableInsideComma(textDisplay)) {
                newTextDisplay = START_POSITION;
            } else {
                if (textDisplay.startsWith(SIGN_VALUE)) {
                    newTextDisplay = textDisplay.substring(1);
                } else {
                    newTextDisplay = SIGN_VALUE + textDisplay;
                }
            }
            DISPLAY.setText(newTextDisplay);
        }
    }

    /**
     * Handler pressing on click the button: '%'.
     */
    @FXML
    public void handlerPercentButton() throws Exception {
        String textDisplay = DISPLAY.getText();
        if (numberOne.isEmpty()) {
            numberOne = PERCENT_PART_NUMBER;
        }
        String calculate = calculate(PERCENT.getId(), numberOne, textDisplay);
        DISPLAY.setText(calculate);
    }

    /**
     * Handler pressing on click the buttons: 'mr', 'mc','m-','m+'.
     *
     * @param event this event is generated when a buttons 'mr', 'mc','m-','m+' is pressed.
     */
    @FXML
    public void handlerMemoryButton(Event event) throws Exception {
        Button btn = (Button) event.getSource();
        String textDisplay = DISPLAY.getText();

        if (btn.equals(MC)) {                                                   // todo    >>> FIX
            numberInMemory = DEFAULT_NUMBER;
        } else if (btn.equals(M_PLUS)) {
            numberInMemory = calculateInMemory(ADDITION.getId(), numberInMemory, textDisplay);
            nextNumber = true;
        } else if (btn.equals(M_MINUS)) {
            numberInMemory = calculateInMemory(SUBTRACTION.getId(), numberInMemory, textDisplay);
            nextNumber = true;
        } else if (btn.equals(MR)) {
            String number = convertBigDecimalToString(numberInMemory);
            DISPLAY.setText(number);
        }
    }

    /**
     * Handler moving applications.
     */
    @FXML
    public void handlerDragMouse() {
        Stage stage = (Stage) DISPLAY.getScene().getWindow();
        DISPLAY.setOnMousePressed(mouseEvent -> {
            POSITION_X = stage.getX() - mouseEvent.getScreenX();
            POSITION_Y = stage.getY() - mouseEvent.getScreenY();
        });
        DISPLAY.setOnMouseDragged(mouseEvent -> {
            stage.setX(mouseEvent.getScreenX() + POSITION_X);
            stage.setY(mouseEvent.getScreenY() + POSITION_Y);
        });
    }

    /**
     * Handler pressing on click the button 'EXIT'.
     */
    @FXML
    public void handleExitButton() {
        System.exit(0);
    }

    /**
     * Handler pressing on click the button 'TRAY'.
     */
    @FXML
    public void handleTrayButton() {
        Stage stage = (Stage) TRAY.getScene().getWindow();
        stage.setIconified(true);
    }

    /**
     * Initialize input data handler with keyboard.
     *
     * @param event this event is generated when a key is pressed, released, or typed.
     */
    @FXML
    public void handlerKeyPressed(KeyEvent event) {
        Button button = findButton(event);
        if (button != null) {
            clickOnButton(button);
        }
    }

    /**
     * Find the button.
     *
     * @param event this event is generated when a key is pressed, released, or typed.
     * @return the button.
     */
    private Button findButton(KeyEvent event) {                                          //todo mapping       >>> FIX
        Button button;
        KeyCode code = event.getCode();
        if (event.isShiftDown()) {
            button = SHORTCUT_MAP.get(code);
        } else {
            button = KEY_MAP.get(code);
        }
        return button;
    }

    /**
     * Change the display size.
     */
    private void changeDisplaySize(int length) {
        int size = MAX_FONT_SIZE_TEXT;
        if (length > ALLOWABLE_MINIMUM_LENGTH_OF_DIGITS) {
            size = MAX_FONT_SIZE_TEXT * ALLOWABLE_MINIMUM_LENGTH_OF_DIGITS / length;
        }
        DISPLAY.setStyle(FX_FONT_SIZE + size + FONT_SIZE);
    }

    /**
     * Click on a given button.
     *
     * @param button the button.
     */
    private void clickOnButton(Button button) {
        PauseTransition pause = new PauseTransition(Duration.seconds(PAUSE_ANIMATION));
        button.arm();
        button.fire();
        pause.setOnFinished(e -> button.disarm());
        pause.play();
    }

    /**
     * Calculate result of the arithmetic operation two numbers.
     *
     * @param operationName the arithmetic operator:  ADDITION, SUBTRACTION, DIVISION, MULTIPLICATION, PERCENT.
     * @param numberOne     the first number.
     * @param numberTwo     the second number.
     * @return result of the arithmetic operation.
     * @throws UndefinedNumberException if division by zero.
     */
    private String calculate(String operationName, String numberOne, String numberTwo) throws UndefinedNumberException {
        Operation operation = Operation.get(operationName);

        BigDecimal firstNumber = new BigDecimal(numberOne);
        BigDecimal secondNumber = new BigDecimal(numberTwo);
        BigDecimal result = CALCULATOR.calculate(operation, firstNumber, secondNumber);
        return convertBigDecimalToString(result);
    }

    /**
     * Calculate adding and subtracting operations from memory
     *
     * @param operationName the operator:  ADDITION, SUBTRACTION.
     * @param numberOne     the first number.
     * @param numberTwo     the second number.
     * @return result of the arithmetic operation.
     * @throws UndefinedNumberException if division by zero.
     */
    private BigDecimal calculateInMemory(String operationName, BigDecimal numberOne, String numberTwo) throws UndefinedNumberException {
        Operation operation = Operation.get(operationName);
        BigDecimal secondNumber = new BigDecimal(numberTwo);
        return CALCULATOR.calculate(operation, numberOne, secondNumber);
    }

    /**
     * Convert the number BigDecimal to a string.
     *
     * @param number BigDecimal
     * @return string representation of the number.
     */
    private String convertBigDecimalToString(BigDecimal number) {                            // todo duplication       >>> FIX
        BigDecimal result = number.stripTrailingZeros();
        if (number.compareTo(MAX_NOT_ENGINEERING_VALUE) > 0 || number.compareTo(MIN_NOT_ENGINEERING_VALUE) < 0) {
            return result.toString().replace(EXPONENT_PLUS_VAL, EXPONENT_VAL);
        }
        return result.toPlainString();
    }

    /**
     * Get priority operations.
     * This method compares two operations.
     *
     * @param displayOperation  the operation selected by the user.
     * @param inMemoryOperation the operation in memory.
     * @return true if the operation in DISPLAY has a higher priority.
     */
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
        return operation.equals(MULTIPLICATION.getId()) || operation.equals(DIVISION.getId());  //todo fix
    }

    /**
     * Verification whether a string is a number.
     *
     * @param number the incoming parameter.
     * @return true if number.
     */
    private boolean isNumber(String number) {
        return number.matches(PATTERN_NUMBER);
    }

    /**
     * Validate the number on the limit count of zeros.
     *
     * @return true if the count of zeros in the permitted limit.
     */
    private boolean validateLimitNumberZeros(String textDisplay) {
        return !textDisplay.startsWith(START_POSITION) || textDisplay.startsWith(ZERO_COMMA);
    }

    /**
     * Validate the number on available inside the comma.
     *
     * @param number the incoming parameter.
     * @return true if the count of commas in the permitted limit.
     */
    private boolean validateNumberAvailableInsideComma(String number) {
        return number.matches(PATTERN_COMA);
    }
}

