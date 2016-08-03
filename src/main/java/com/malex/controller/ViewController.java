package com.malex.controller;

import com.malex.model.enums.Operation;
import com.malex.model.exception.UndefinedNumberException;
import javafx.animation.PauseTransition;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static com.malex.model.Calculator.CALCULATOR;
import static com.malex.model.enums.Operation.*;
import static javafx.scene.input.KeyCombination.META_ANY;

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
     * The minimum number of characters after which the screen font changes.
     */
    private static final int ALLOWABLE_MINIMUM_LENGTH_OF_DIGITS = 7;

    /**
     * Maximum permissible length of the input numbers on the display.
     */
    private static final int MAXIMUM_LENGTH = 30;

    /**
     * The initial value of which is displayed on the display screen of the calculator.
     */
    private static final String DEFAULT_VALUE = "0";

    /**
     * The empty string value.
     */
    private static final String EMPTY_VALUE = "";

    /**
     * The maximum font size of the text displayed on the screen.
     */
    private static final int MAX_FONT_SIZE_TEXT = 46;

    /**
     * Pause animation when pressing the button.
     */
    private static final double PAUSE_ANIMATION = 0.1;

    /**
     * One hundredth part of the number is used in interest calculation.
     */
    private static final BigDecimal ONE_HUNDREDTH_PART_NUMBER = new BigDecimal("1");

    /**
     * The number is undefined.
     */
    private final static String UNDEFINED = "Undefined";

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
    private static final Font FONT_APP = new Font("Helvetica Neue Thin", MAX_FONT_SIZE_TEXT);

    /**
     * The default number in memory for an operation: mr; mc; M+; M-.
     */
    private static final BigDecimal DEFAULT_NUMBER = new BigDecimal(DEFAULT_VALUE);

    /**
     * Display the exponent on display
     */
    private static final String EXPONENT_VALUE = "E";
    /**
     * Display the exponent in model.
     */
    private static final String EXPONENT_PLUS_VALUE = "E+";

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
    private static final String ZERO_COMMA_VALUE = "0.";

    /**
     * The value '.'.
     */
    private static final String COMMA_VALUE = ".";

    /**
     * The value '-'.
     */
    private static final String MINUS_VALUE = "-";

    /**
     * Mapping the buttons and combinations of keyboard buttons.
     */
    private static final Map<KeyCodeCombination, Button> MAP_KEY_CODE = new HashMap<>();

    /**
     * Value is used to store ID buttons.
     */
    @FXML
    public TextField display;
    @FXML
    public Button mr;
    @FXML
    public Button mc;
    @FXML
    public Button mPlus;
    @FXML
    public Button mMinus;
    @FXML
    public Button digit0;
    @FXML
    public Button digit1;
    @FXML
    public Button digit2;
    @FXML
    public Button digit3;
    @FXML
    public Button digit4;
    @FXML
    public Button digit5;
    @FXML
    public Button digit6;
    @FXML
    public Button digit7;
    @FXML
    public Button digit8;
    @FXML
    public Button digit9;
    @FXML
    public Button comma;
    @FXML
    public Button multiplication;
    @FXML
    public Button division;
    @FXML
    public Button addition;
    @FXML
    public Button subtraction;
    @FXML
    public Button equals;
    @FXML
    public Button escape;
    @FXML
    public Button sign;
    @FXML
    public Button percent;
    @FXML
    public Button exit;
    @FXML
    public Button tray;

    /**
     * Position on the x-axis.
     */
    private static double POSITION_X;

    /**
     * Position on the y-axis.
     */
    private static double POSITION_Y;

    /**
     * The arithmetic operator in memory for an operation: mr; mc; M+; M-.
     */
    private static BigDecimal numberInMemory = new BigDecimal(DEFAULT_VALUE);

    /**
     * The first number stored in memory.
     */
    private BigDecimal numberOne;

    /**
     * The second number stored in memory.
     */
    private BigDecimal numberTwo;

    /**
     * The arithmetic operator in memory.
     */
    private Operation operator;

    /**
     * The priority an operator in memory.
     */
    private Operation operatorInMemory;

    /**
     * Indicate next a number.
     */
    private boolean nextNumber;

    /**
     * Specifies starting position.
     */
    private boolean startPosition = true;

    /**
     * The status of reuse arithmetic operators: '+', '-', '*', '/'.
     */
    private boolean replaceOperator;

    /**
     * The status of reuse operators 'equals'.
     */
    private boolean reuseOperatorEquals;

    /**
     * Specifies the priority of the operation.
     */
    private boolean isPriorityOperations;

    /**
     * Initialization the controller.
     */
    public void init() {
        display.setEditable(false);
        display.setFont(FONT_APP);
        display.setText(DEFAULT_VALUE);
        display.lengthProperty().addListener((observable, oldValue, newValue) -> {
            changeDisplaySize(newValue.intValue());
        });
    }

    /**
     * Initialization the mapping of buttons.
     */
    @FXML
    void initialize() {
        MAP_KEY_CODE.put(new KeyCodeCombination(KeyCode.DIGIT0, META_ANY), digit0);
        MAP_KEY_CODE.put(new KeyCodeCombination(KeyCode.DIGIT1, META_ANY), digit1);
        MAP_KEY_CODE.put(new KeyCodeCombination(KeyCode.DIGIT2, META_ANY), digit2);
        MAP_KEY_CODE.put(new KeyCodeCombination(KeyCode.DIGIT3, META_ANY), digit3);
        MAP_KEY_CODE.put(new KeyCodeCombination(KeyCode.DIGIT4, META_ANY), digit4);
        MAP_KEY_CODE.put(new KeyCodeCombination(KeyCode.DIGIT5, META_ANY), digit5);
        MAP_KEY_CODE.put(new KeyCodeCombination(KeyCode.DIGIT6, META_ANY), digit6);
        MAP_KEY_CODE.put(new KeyCodeCombination(KeyCode.DIGIT7, META_ANY), digit7);
        MAP_KEY_CODE.put(new KeyCodeCombination(KeyCode.DIGIT8, META_ANY), digit8);
        MAP_KEY_CODE.put(new KeyCodeCombination(KeyCode.DIGIT9, META_ANY), digit9);

        MAP_KEY_CODE.put(new KeyCodeCombination(KeyCode.COMMA, META_ANY), comma);
        MAP_KEY_CODE.put(new KeyCodeCombination(KeyCode.PERIOD, META_ANY), comma);
        MAP_KEY_CODE.put(new KeyCodeCombination(KeyCode.SLASH, META_ANY), division);
        MAP_KEY_CODE.put(new KeyCodeCombination(KeyCode.MINUS, META_ANY), subtraction);
        MAP_KEY_CODE.put(new KeyCodeCombination(KeyCode.EQUALS, META_ANY), equals);
        MAP_KEY_CODE.put(new KeyCodeCombination(KeyCode.ENTER, META_ANY), equals);
        MAP_KEY_CODE.put(new KeyCodeCombination(KeyCode.ESCAPE, META_ANY), escape);
        MAP_KEY_CODE.put(new KeyCodeCombination(KeyCode.C, META_ANY), mc);
        MAP_KEY_CODE.put(new KeyCodeCombination(KeyCode.R, META_ANY), mr);
        MAP_KEY_CODE.put(new KeyCodeCombination(KeyCode.M, META_ANY), mMinus);
        MAP_KEY_CODE.put(new KeyCodeCombination(KeyCode.P, META_ANY), mPlus);

        MAP_KEY_CODE.put(new KeyCodeCombination(KeyCode.EQUALS, KeyCombination.SHIFT_DOWN), addition);
        MAP_KEY_CODE.put(new KeyCodeCombination(KeyCode.DIGIT8, KeyCombination.SHIFT_DOWN), multiplication);
        MAP_KEY_CODE.put(new KeyCodeCombination(KeyCode.MINUS, KeyCombination.SHIFT_DOWN), sign);
        MAP_KEY_CODE.put(new KeyCodeCombination(KeyCode.DIGIT5, KeyCombination.SHIFT_DOWN), percent);
    }

    /**
     * Handler pressing on click the buttons: '1', '2', '3', '4', '5', '6', '7', '8', '9', '0'.
     *
     * @param event this event is generated when a buttons is pressed.
     */
    @FXML
    public void pushNumbersButton(Event event) {
        String textDisplay = display.getText();
        if (!isNumber(textDisplay)) {
            textDisplay = EMPTY_VALUE;
            numberOne = null;
        } else {
            if (textDisplay.equals(DEFAULT_VALUE) && startPosition) {
                textDisplay = EMPTY_VALUE;
                startPosition = false;
            } else if (textDisplay.startsWith(DEFAULT_VALUE) && textDisplay.length() == 1) {
                textDisplay = EMPTY_VALUE;
            }
        }
        if (nextNumber) {
            textDisplay = EMPTY_VALUE;
            nextNumber = false;
        }
        replaceOperator = false;

        if (textDisplay.length() < MAXIMUM_LENGTH) {
            Button btn = (Button) event.getSource();

            int number = -1;
            String coma = EMPTY_VALUE;

            if (btn.equals(digit0)) {
                if (validateLimitNumberZeros(textDisplay)) {
                    number = 0;
                }
            } else if (btn.equals(digit1)) {
                number = 1;
            } else if (btn.equals(digit2)) {
                number = 2;
            } else if (btn.equals(digit3)) {
                number = 3;
            } else if (btn.equals(digit4)) {
                number = 4;
            } else if (btn.equals(digit5)) {
                number = 5;
            } else if (btn.equals(digit6)) {
                number = 6;
            } else if (btn.equals(digit7)) {
                number = 7;
            } else if (btn.equals(digit8)) {
                number = 8;
            } else if (btn.equals(digit9)) {
                number = 9;
            } else if (btn.equals(comma)) {
                if (textDisplay.isEmpty()) {
                    coma = ZERO_COMMA_VALUE;
                } else if (!textDisplay.contains(COMMA_VALUE)) {
                    coma = COMMA_VALUE;
                }
            }

            if (number != -1) {
                textDisplay = textDisplay + number;
            }

            display.setText(textDisplay + coma);
        }
    }

    /**
     * Handler pressing on click the buttons: '=', '+', '-', '*', '/'.
     *
     * @param event this event is generated when a buttons '=', '+', '-', '*', '/' is pressed.
     */
    @FXML
    public void pushOperationButton(Event event) {
        Button btn = (Button) event.getSource();
        Operation operatorValue = Operation.get(btn.getId());

        String textDisplay = display.getText();

        try {
            BigDecimal numbers;

            if (textDisplay.equals(UNDEFINED)) {
                throw new UndefinedNumberException("The number is Undefined!");

            } else {
                numbers = convertStringToBigDecimal(textDisplay);

                if (equals != btn) {
                    if (reuseOperatorEquals) {
                        numberOne = numbers;
                        operator = operatorValue;
                        numberTwo = null;
                        nextNumber = true;
                        reuseOperatorEquals = false;
                    } else {
                        if (replaceOperator) {
                            replaceOperator = true;
                            operator = operatorValue;
                        } else {
                            if (operator != null && operatorInMemory == null) {
                                if (getPriorityOperations(operatorValue, operator)) {
                                    operatorInMemory = operatorValue;
                                    isPriorityOperations = true;
                                    numberTwo = numbers;
                                    nextNumber = true;
                                    return;
                                }
                            }
                            if (!isPriorityOperations) {
                                if (numberTwo == null && operator == null) {
                                    operator = operatorValue;
                                    numberOne = numbers;
                                    nextNumber = true;
                                } else {
                                    numberOne = CALCULATOR.calculate(operator, numberOne, numbers);
                                    operator = operatorValue;
                                    nextNumber = true;
                                    display.setText(convertBigDecimalToString(numberOne));
                                }
                            } else {
                                numberTwo = CALCULATOR.calculate(operatorInMemory, numberTwo, numbers);
                                display.setText(convertBigDecimalToString(numberOne));
                                if (isHighPriorityOperation(operatorInMemory)) {
                                    operatorInMemory = operatorValue;
                                    isPriorityOperations = true;
                                } else {
                                    operatorInMemory = null;
                                    numberOne = CALCULATOR.calculate(operator, numberOne, numberTwo);
                                    operator = operatorValue;
                                    numberTwo = null;
                                    isPriorityOperations = false;
                                }
                                nextNumber = true;
                            }
                            replaceOperator = true;
                        }
                    }
                } else {
                    if (operator != null) {
                        if (isPriorityOperations) {
                            numberTwo = CALCULATOR.calculate(operatorInMemory, numberTwo, numbers);
                        }
                        if (numberTwo == null) {
                            numberTwo = convertStringToBigDecimal(display.getText());
                        }
                        if (numberOne == null) {
                            numberOne = numberTwo;
                        }
                        if (!operator.equals(Operation.EQUALS)) {
                            BigDecimal calculate = CALCULATOR.calculate(operator, numberOne, numberTwo);
                            reuseOperatorEquals = true;
                            if (numberOne != null) {
                                numberOne = calculate;
                            }
                            if (isPriorityOperations) {
                                operator = operatorInMemory;
                                numberOne = numbers;
                                numberTwo = null;
                                operatorInMemory = null;
                                isPriorityOperations = false;
                            }
                            numbers = calculate;
                        }
                    }
                    display.setText(convertBigDecimalToString(numbers));
                }
            }
        } catch (UndefinedNumberException e) {
            display.setText(UNDEFINED);
            operator = operatorValue;
        } catch (RuntimeException e) {
            logger.error("Exception type: " + e.getClass().getSimpleName() + " -> pushOperationButton(Event event): " + e.getMessage());
        }
    }

    /**
     * Handler pressing on click the button: 'AC'.
     */
    @FXML
    public void pushResetButton() {
        operator = null;
        numberOne = null;
        numberTwo = null;
        startPosition = true;
        isPriorityOperations = false;
        reuseOperatorEquals = false;
        display.setStyle(FX_FONT_SIZE + MAX_FONT_SIZE_TEXT + FONT_SIZE);
        display.setText(DEFAULT_VALUE);
    }

    /**
     * Handler pressing on click the button: '-'.
     */
    @FXML
    public void pushMinusButton() {
        String textDisplay = display.getText();
        if (!textDisplay.equals(DEFAULT_VALUE)) {
            String newTextDisplay;

            if (validateNumberAvailableInsideComma(textDisplay)) {
                newTextDisplay = DEFAULT_VALUE;
            } else {
                if (textDisplay.startsWith(MINUS_VALUE)) {
                    newTextDisplay = textDisplay.substring(MINUS_VALUE.length());
                } else {
                    newTextDisplay = MINUS_VALUE + textDisplay;
                }
            }
            display.setText(newTextDisplay);
        }
    }

    /**
     * Handler pressing on click the button: '%'.
     */
    @FXML
    public void pushPercentButton() {
        String text = display.getText();
        try {
            if (!text.equals(UNDEFINED)) {
                BigDecimal textDisplay = new BigDecimal(text);

                if (numberOne == null) {
                    numberOne = ONE_HUNDREDTH_PART_NUMBER;
                }

                BigDecimal result = CALCULATOR.calculate(PERCENT, numberOne, textDisplay);
                display.setText(convertBigDecimalToString(result));
            }
        } catch (UndefinedNumberException e) {
            display.setText(UNDEFINED);
        }
    }

    /**
     * Handler pressing on click the buttons: 'mr', 'mc','m-','m+'.
     *
     * @param event this event is generated when a buttons 'mr', 'mc','m-','m+' is pressed.
     */
    @FXML
    public void pushMemoryButton(Event event) throws UndefinedNumberException {
        Button btn = (Button) event.getSource();

        String textDisplay = display.getText();

        if (!textDisplay.equals(UNDEFINED)) {
            BigDecimal number = convertStringToBigDecimal(textDisplay);

            if (btn == mc) {
                numberInMemory = DEFAULT_NUMBER;
            } else if (btn == mPlus) {
                numberInMemory = CALCULATOR.calculate(ADDITION, numberInMemory, number);
                nextNumber = true;
            } else if (btn == mMinus) {
                numberInMemory = CALCULATOR.calculate(SUBTRACTION, numberInMemory, number);
                nextNumber = true;
            } else if (btn == mr) {
                String result = convertBigDecimalToString(numberInMemory);
                display.setText(result);
            }
        }
    }

    /**
     * Move an application by pressing the right mouse button.
     */
    @FXML
    public void mouseDragOver() {
        Stage stage = (Stage) display.getScene().getWindow();
        display.setOnMousePressed(mouseEvent -> {
            POSITION_X = stage.getX() - mouseEvent.getScreenX();
            POSITION_Y = stage.getY() - mouseEvent.getScreenY();
        });
        display.setOnMouseDragged(mouseEvent -> {
            stage.setX(mouseEvent.getScreenX() + POSITION_X);
            stage.setY(mouseEvent.getScreenY() + POSITION_Y);
        });
    }

    /**
     * Shutdown the application.
     */
    @FXML
    public void exitApplication() {
        System.exit(0);
    }

    /**
     * Minimize application to the system tray
     */
    @FXML
    public void minimizeApplication() {
        Stage stage = (Stage) tray.getScene().getWindow();
        stage.setIconified(true);
    }

    /**
     * Initialize input data handler with keyboard.
     *
     * @param event this event is generated when a key is pressed, released, or typed.
     */
    @FXML
    public void keyPressed(KeyEvent event) {
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
        for (KeyCodeCombination combination : MAP_KEY_CODE.keySet()) {
            if (combination.match(event)) {
                return MAP_KEY_CODE.get(combination);
            }
        }
        return null;
    }

    /**
     * Change the display size.
     */
    private void changeDisplaySize(int length) {
        int size = MAX_FONT_SIZE_TEXT;
        if (length > ALLOWABLE_MINIMUM_LENGTH_OF_DIGITS) {
            size = MAX_FONT_SIZE_TEXT * ALLOWABLE_MINIMUM_LENGTH_OF_DIGITS / length;
        }
        display.setStyle(FX_FONT_SIZE + size + FONT_SIZE);
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
     * Convert the number BigDecimal to String.
     *
     * @param number BigDecimal
     * @return string representation of the number.
     */
    private String convertBigDecimalToString(BigDecimal number) {
        BigDecimal result = number.stripTrailingZeros();
        if (number.compareTo(MAX_NOT_ENGINEERING_VALUE) > 0 || number.compareTo(MIN_NOT_ENGINEERING_VALUE) < 0) {
            return result.toString().replace(EXPONENT_PLUS_VALUE, EXPONENT_VALUE);
        }
        return result.toPlainString();
    }

    /**
     * Convert the String to BigDecimal.
     *
     * @param number string
     * @return BigDecimal.
     */
    private BigDecimal convertStringToBigDecimal(String number) {
        return new BigDecimal(number);
    }

    /**
     * Get priority operations.
     * This method compares two operations.
     *
     * @param displayOperation  the operation selected by the user.
     * @param inMemoryOperation the operation in memory.
     * @return true if the operation in display has a higher priority.
     */
    private boolean getPriorityOperations(Operation displayOperation, Operation inMemoryOperation) {
        return isHighPriorityOperation(displayOperation) && !isHighPriorityOperation(inMemoryOperation);
    }

    /**
     * Get priority operation.
     *
     * @param operation the arithmetic operation
     * @return true if the operation has a higher priority.
     */
    private boolean isHighPriorityOperation(Operation operation) {
        return operation.equals(MULTIPLICATION) || operation.equals(DIVISION);
    }

    /**
     * Verification incoming string.
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
        return !textDisplay.startsWith(DEFAULT_VALUE) || textDisplay.startsWith(ZERO_COMMA_VALUE);
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

