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
     * Value is used to store allowable minimum length of digits.
     */
    private static final int ALLOWABLE_MINIMUM_LENGTH_OF_DIGITS = 7;
    /**
     * Value is used to store maximum length of digits.
     */
    private static final int MAXIMUM_LENGTH = 30;

    /**
     * Value is used to store the cursor.
     */
    private static final String START_CURSOR_POSITION = "0";

    /**
     * Value is used to store the reset number.
     */
    private static final String RESET_NUMBER = "";

    /**
     * Value is used to store the maximum font size a text.
     */
    private static final int MAX_FONT_SIZE_TEXT = 46;

    /**
     * Value is used to store the text font.
     */
    private static final String TEXT_FONT = "Helvetica Neue Thin";

    /**
     * The value is used to store the value of the PAUSE of the animation.
     */
    private static final double PAUSE_ANIMATION = 0.1;

    /**
     * Value is used to store the value the default percent of number '1'.
     */
    private static final String PERCENT_NUMBER_DEFAULT = "1";

    /**
     * Value is used to store the pattern of validate the number.
     */
    private final static String PATTERN_NUMBER = "[-+0-9|.E]+";

    /**
     * Value is used to store the pattern of validate the number.
     */
    private final static String PATTERN_COMA = "^0.[0]+|(0.)??";

    /**
     * Value is used to store the style font size.
     */
    private final static String FX_FONT_SIZE = "-fx-font-size: ";

    /**
     * Value is used to store the font size.
     */
    private final static String FONT_SIZE = "px;";

    /**
     * Value is used to store the font.
     */
    private final static Font FONT_APP;

    /**
     * Value is used to store exponent.
     */
    private final static String EXPONENT_VAL = "E";
    /**
     * Value is used to store exponent.
     */
    private final static String EXPONENT_PLUS_VAL = "E+";

    /**
     * Value is used to store the maximum number for engineering calculations.
     */
    private final static BigDecimal MAX_VALUE;

    /**
     * Value is used to store the minimum number for engineering calculations.
     */
    private final static BigDecimal MIN_VALUE;

    /**
     * Initialization.
     */
    static {
        FONT_APP = new Font(TEXT_FONT, MAX_FONT_SIZE_TEXT);
        MAX_VALUE = new BigDecimal("9999999999999999");
        MIN_VALUE = new BigDecimal("-999999999999999");
    }

    /**
     * Value is used to store the value '0.'.
     */
    private static final String ZERO_COMMA = "0.";

    /**
     * Value is used to store the value '.'.
     */
    private static final String COMMA_VALUE = ".";

    /**
     * Value is used to store the value '-'.
     */
    private static final String SIGN_VALUE = "-";

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
    private static final String ID_ADD = "ADDITION";
    private static final String ID_SUBTRACTION = "SUBTRACTION";
    private static final String ID_MULTIPLICATION = "MULTIPLICATION";
    private static final String ID_DIVISION = "DIVISION";
    private static final String ID_PERCENT = "PERCENT";
    private static final String ID_COMMA = "COMMA";
    private static final String ID_EQUALS = "EQUALS";

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
     * Value is used to store the position on the x-axis.
     */
    private static double POSITION_X;

    /**
     * Value is used to store the position on the y-axis.
     */
    private static double POSITION_Y;

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
     * Value is used to store the priority an operator in memory.
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
        DISPLAY.setText(START_CURSOR_POSITION);
        DISPLAY.lengthProperty().addListener((observable, oldValue, newValue) -> {
            changeDisplaySize(newValue.intValue());
        });
    }

    /**
     * Handler pressing on click the buttons: '1', '2', '3', '4', '5', '6', '7', '8', '9', '0'.
     *
     * @param event this event is generated when a buttons is pressed.
     */
    @FXML
    public void handlerNumbersButton(Event event) {
        String value = DISPLAY.getText();
        if (!isNumber(value)) {
            value = RESET_NUMBER;
            numberOne = RESET_NUMBER;
        } else {
            if (value.equals(START_CURSOR_POSITION) && startPosition) {
                value = RESET_NUMBER;
                startPosition = false;
            } else if (value.startsWith(START_CURSOR_POSITION) && value.length() == 1) {
                value = RESET_NUMBER;
            }
        }
        if (nextNumber) {
            value = RESET_NUMBER;
            nextNumber = false;
        }
        DISPLAY.setText(value);//todo remove code duplicates

        replaceOperator = false;
        if (value.length() < MAXIMUM_LENGTH) {
            Button btn = (Button) event.getSource();
            String number = RESET_NUMBER;
            switch (btn.getId()) {
                case ID_ZERO:
                    if (validateLimitNumberZeros(value)) {
                        number = START_CURSOR_POSITION;
                    }
                    break;
                case ID_ONE:
                    number = "1";
                    break;
                case ID_TWO:
                    number = "2";
                    break;
                case ID_THREE:
                    number = "3";
                    break;
                case ID_FOUR:
                    number = "4";
                    break;
                case ID_FIVE:
                    number = "5";
                    break;
                case ID_SIX:
                    number = "6";
                    break;
                case ID_SEVEN:
                    number = "7";
                    break;
                case ID_EIGHT:
                    number = "8";
                    break;
                case ID_NINE:
                    number = "9";
                    break;
                case ID_COMMA:
                    if (value.isEmpty()) {
                        number = ZERO_COMMA;
                    } else if (!value.contains(COMMA_VALUE)) {
                        number = COMMA_VALUE;
                    }
                    break;
                default:
                    break;
            }
            DISPLAY.appendText(number);
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
        String tempNumber = DISPLAY.getText();
        try {
            if (!ID_EQUALS.equals(operatorValue)) {
                if (operatorEqualsUsed) {
                    numberOne = tempNumber;
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
                                numberTwo = tempNumber;
                                nextNumber = true;
                                return;
                            }
                        }
                        if (!isPriorityOperations) {
                            if (numberTwo.isEmpty() && operator.isEmpty()) {
                                operator = operatorValue;
                                numberOne = tempNumber;
                                nextNumber = true;
                            } else {
                                numberOne = calculate(operator, numberOne, tempNumber);
                                operator = operatorValue;
                                nextNumber = true;
                                DISPLAY.setText(numberOne);
                            }
                        } else {
                            numberTwo = calculate(operatorInMemory, numberTwo, tempNumber);
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
                        numberTwo = calculate(operatorInMemory, numberTwo, tempNumber);
                    }
                    if (numberTwo.isEmpty()) {
                        numberTwo = DISPLAY.getText();
                    }
                    if (numberOne.equals(RESET_NUMBER)) {
                        numberOne = numberTwo;
                    }
                    if (!operator.equals(ID_EQUALS)) {
                        String calculate = calculate(operator, numberOne, numberTwo);
                        operatorEqualsUsed = true;
                        if (!numberOne.isEmpty()) {
                            numberOne = calculate;
                        }
                        if (isPriorityOperations) {
                            operator = operatorInMemory;
                            numberOne = tempNumber;
                            numberTwo = RESET_NUMBER;
                            operatorInMemory = RESET_NUMBER;
                            isPriorityOperations = false;
                        }
                        tempNumber = calculate;
                    }
                } else {
                    if (validateNumberAvailableInsideComma(tempNumber)) {
                        tempNumber = START_CURSOR_POSITION;
                    }
                }
                DISPLAY.setText(tempNumber);
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
        DISPLAY.setText(START_CURSOR_POSITION);
    }

    /**
     * Handler pressing on click the button: '-'.
     */
    @FXML
    public void handlerSingButton() {
        String number = DISPLAY.getText();
        if (!number.equals(START_CURSOR_POSITION)) {
            String textDisplay = START_CURSOR_POSITION;
            if (!validateNumberAvailableInsideComma(number)) {
                if (number.startsWith(SIGN_VALUE)) {
                    textDisplay = number.substring(0, 0) + number.substring(1);
                } else {
                    textDisplay = SIGN_VALUE + number;
                }
            }
            DISPLAY.setText(textDisplay);
        }
    }

    /**
     * Handler pressing on click the button: '%'.
     */
    @FXML
    public void handlerPercentButton() throws Exception {
        String textDisplay = DISPLAY.getText();
        if (numberOne.isEmpty()) {
            numberOne = PERCENT_NUMBER_DEFAULT;
        }
        String calculate = calculate(ID_PERCENT, numberOne, textDisplay);
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
        switch (btn.getId()) {
            case "MC":
                numberInMemory = START_CURSOR_POSITION;
                break;
            case "M_PLUS":
                numberInMemory = calculate(ID_ADD, numberInMemory, textDisplay);
                nextNumber = true;
                break;
            case "M_MINUS":
                numberInMemory = calculate(ID_SUBTRACTION, numberInMemory, textDisplay);
                nextNumber = true;
                break;
            case "MR":
                DISPLAY.setText(numberInMemory);
                break;
            default:
                break;
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
    private Button findButton(KeyEvent event) {
        Button button;
        KeyCode code = event.getCode();
        if (event.isShiftDown()) {
            switch (code) {
                case EQUALS:
                    button = ADDITION;
                    break;
                case DIGIT8:
                    button = MULTIPLICATION;
                    break;
                case MINUS:
                    button = SIGN;
                    break;
                case DIGIT5:
                    button = PERCENT;
                    break;
                default:
                    button = null;
                    break;
            }
        } else {
            switch (code) {
                case DIGIT0:
                    button = DIGIT0;
                    break;
                case DIGIT1:
                    button = DIGIT1;
                    break;
                case DIGIT2:
                    button = DIGIT2;
                    break;
                case DIGIT3:
                    button = DIGIT3;
                    break;
                case DIGIT4:
                    button = DIGIT4;
                    break;
                case DIGIT5:
                    button = DIGIT5;
                    break;
                case DIGIT6:
                    button = DIGIT6;
                    break;
                case DIGIT7:
                    button = DIGIT7;
                    break;
                case DIGIT8:
                    button = DIGIT8;
                    break;
                case DIGIT9:
                    button = DIGIT9;
                    break;
                case COMMA:
                    button = COMMA;
                    break;
                case SLASH:
                    button = DIVISION;
                    break;
                case MINUS:
                    button = SUBTRACTION;
                    break;
                case EQUALS:
                    button = EQUALS;
                    break;
                case ENTER:
                    button = EQUALS;
                    break;
                case ESCAPE:
                    button = ESCAPE;
                    break;
                case C:
                    button = MC;
                    break;
                case R:
                    button = MR;
                    break;
                case M:
                    button = M_MINUS;
                    break;
                case P:
                    button = M_PLUS;
                    break;
                default:
                    button = null;
                    break;
            }
        }
        return button;
    }

    /**
     * Change the DISPLAY size.
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
        Operation name = Operation.get(operationName);

        BigDecimal firstNumber = new BigDecimal(numberOne);
        BigDecimal secondNumber = new BigDecimal(numberTwo);
        BigDecimal result = CALCULATOR.calculate(name, firstNumber, secondNumber);

        if (result.compareTo(MAX_VALUE) > 0 || result.compareTo(MIN_VALUE) < 0) {
            return result.stripTrailingZeros().toString().replace(EXPONENT_PLUS_VAL, EXPONENT_VAL);
        }
        return result.stripTrailingZeros().toPlainString();
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
        return operation.equals(ID_MULTIPLICATION) || operation.equals(ID_DIVISION);
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
        return !textDisplay.startsWith(START_CURSOR_POSITION) || textDisplay.startsWith(ZERO_COMMA);
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

