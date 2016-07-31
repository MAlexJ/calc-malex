package com.malex.controller;

import com.malex.model.exception.UndefinedNumberException;
import javafx.animation.PauseTransition;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.apache.log4j.Logger;

import java.util.regex.Pattern;

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
    private static final double PAUSE_ANIMATION = 0.08;

    /**
     * Value is used to store the value the default percent of number '1'.
     */
    private static final String PERCENT_NUMBER_DEFAULT = "1";

    private final static String PATTERN_NUMBER = "[-+0-9|.E]+"; //TODO ok

    private final static String PATTERN_COMA = "^0.[0]+|(0.)??"; //TODO ok

    private final static String FX_FONT_SIZE = "-fx-font-size: "; //TODo OK

    private final static String FONT_SIZE = "px;"; //TODo OK

    private final static Font FONT_APP; // TODO -> ok

    static {
        FONT_APP = new Font(TEXT_FONT, MAX_FONT_SIZE_TEXT); // TODO -> ok
    }

    /**
     * Value is used to store the value '0.'.
     */
    private static final String ZERO_COMMA = "0.";

    /**
     * Value is used to store the value '.'.
     */
    private static final String COMMA_VAL = ".";

    /**
     * Value is used to store the value '-'.
     */
    private static final String SIGN_VAL = "-";

    /**
     * Value is used to store the value '_'.
     */
    private static final String UNDERSCORE_VAL = "_";
    /**
     * Value is used to store the value '*'.
     */
    private static final String STAR_VAL = "*";
    /**
     * Value is used to store the value ';'.
     */
    private static final String SEMICOLON_VAL = ";";
    /**
     * Value is used to store the value '%'.
     */
    private static final String PERCENT_VAL = "%";
    /**
     * Value is used to store the value ':'.
     */
    private static final String COLON_VAL = ":";
    /**
     * Value is used to store the value '+'.
     */
    private static final String PLUS_VAL = "+";
    /**
     * Value is used to store the value 'MINUS'.
     */
    private static final String MINUS_VAL = "MINUS";
    /**
     * Value is used to store the value 'SLASH'.
     */
    private static final String SLASH_VAL = "SLASH";
    /**
     * Value is used to store the value 'ENTER'.
     */
    private static final String ENTER_VAL = "ENTER";
    /**
     * Value is used to store the value 'PERIOD'.
     */
    private static final String PERIOD_VAL = "PERIOD";
    /**
     * Value is used to store the value 'C'.
     */
    private static final String C_VAL = "C";
    /**
     * Value is used to store the value 'R'.
     */
    private static final String R_VAL = "R";
    /**
     * Value is used to store the value 'P'.
     */
    private static final String P_VAL = "P";
    /**
     * Value is used to store the value 'M'.
     */
    private static final String M_VAL = "M";

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
    private static final String ID_MR = "MR";
    private static final String ID_MC = "MC";
    private static final String ID_M_PLUS = "M_PLUS";
    private static final String ID_M_MINUS = "M_MINUS";
    private static final String ID_RESET = "ESCAPE";

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
            DISPLAY.setText(RESET_NUMBER);
            numberOne = RESET_NUMBER;
        }

        if (value.equals(START_CURSOR_POSITION) && startPosition) {
            DISPLAY.setText(RESET_NUMBER);
            startPosition = false;
        }
        if (value.startsWith(START_CURSOR_POSITION) && DISPLAY.getText().length() == 1) {
            DISPLAY.setText(RESET_NUMBER);
        }
        if (nextNumber) {
            DISPLAY.setText(RESET_NUMBER);
            nextNumber = false;
        }
        replaceOperator = false;

        if (DISPLAY.getText().length() >= MAXIMUM_LENGTH) {
            return;
        } // TODO else ->>>>>

        Button btn = (Button) event.getSource();
        switch (btn.getId()) {
            case ID_ZERO:
                if (validateLimitNumberZeros()) {
                    DISPLAY.appendText(START_CURSOR_POSITION);
                }
                break;
            case ID_ONE:
                DISPLAY.appendText("1");
                break;
            case ID_TWO:
                DISPLAY.appendText("2");
                break;
            case ID_THREE:
                DISPLAY.appendText("3");
                break;
            case ID_FOUR:
                DISPLAY.appendText("4");
                break;
            case ID_FIVE:
                DISPLAY.appendText("5");
                break;
            case ID_SIX:
                DISPLAY.appendText("6");
                break;
            case ID_SEVEN:
                DISPLAY.appendText("7");
                break;
            case ID_EIGHT:
                DISPLAY.appendText("8");
                break;
            case ID_NINE:
                DISPLAY.appendText("9");
                break;
            case ID_COMMA:

//                if (DISPLAY.getText().isEmpty()) {
//                    DISPLAY.appendText(ZERO_COMMA);
//                }
//                if (!DISPLAY.getText().contains(COMMA_VAL)) {
//                    DISPLAY.appendText(COMMA_VAL);
//                }

                if (DISPLAY.getText().isEmpty()) {
                    DISPLAY.appendText(ZERO_COMMA);
                }
                if (!DISPLAY.getText().contains(COMMA_VAL)) {
                    DISPLAY.appendText(COMMA_VAL);
                }

                break;
            default:
                break;
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
                            }else{
                                // there
                            }
                        }

                        if (!isPriorityOperations) {
                            if (numberTwo.isEmpty() && operator.isEmpty()) {
                                operator = operatorValue;
                                numberOne = tempNumber;
                                nextNumber = true;
                            } else {
                                numberOne = CALCULATOR.calculate(operator, numberOne, tempNumber);
                                operator = operatorValue;
                                nextNumber = true;
                                DISPLAY.setText(numberOne);
                            }
                        } else {
                            numberTwo = CALCULATOR.calculate(operatorInMemory, numberTwo, tempNumber);
                            DISPLAY.setText(numberTwo);
                            if (isHighPriorityOperation(operatorInMemory)) {
                                operatorInMemory = operatorValue;
                                isPriorityOperations = true;
                            } else {
                                operatorInMemory = RESET_NUMBER;
                                numberOne = CALCULATOR.calculate(operator, numberOne, numberTwo);
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
                        numberTwo = CALCULATOR.calculate(operatorInMemory, numberTwo, tempNumber);
                    }
                    if (numberTwo.isEmpty()) {
                        numberTwo = DISPLAY.getText();
                    }
                    if (numberOne.equals(RESET_NUMBER)) {
                        numberOne = numberTwo;
                    }
                    if (!operator.equals(ID_EQUALS)) {
                        String calculate = CALCULATOR.calculate(operator, numberOne, numberTwo);
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
        } catch (Exception e) {
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
                if (number.startsWith(SIGN_VAL)) {
                    textDisplay = number.substring(0, 0) + number.substring(1);
                } else {
                    textDisplay = SIGN_VAL + number;
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
        String calculate = CALCULATOR.calculate(ID_PERCENT, numberOne, textDisplay);
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
                numberInMemory = CALCULATOR.calculate(ID_ADD, numberInMemory, textDisplay);
                nextNumber = true;
                break;
            case "M_MINUS":
                numberInMemory = CALCULATOR.calculate(ID_SUBTRACTION, numberInMemory, textDisplay);
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
     * Initialize keyboard input data handler.
     *
     * @param event this event is generated when a key is pressed, released, or typed.
     */
    @FXML
    public void handlerKeyPressed(KeyEvent event) {
        String nameKey = shortcuts(event);
        switch (nameKey) {
            case ID_ZERO:
                clickOnButton(DIGIT0);
                break;
            case ID_ONE:
                clickOnButton(DIGIT1);
                break;
            case ID_TWO:
                clickOnButton(DIGIT2);
                break;
            case ID_THREE:
                clickOnButton(DIGIT3);
                break;
            case ID_FOUR:
                clickOnButton(DIGIT4);
                break;
            case ID_FIVE:
                clickOnButton(DIGIT5);
                break;
            case ID_SIX:
                clickOnButton(DIGIT6);
                break;
            case ID_SEVEN:
                clickOnButton(DIGIT7);
                break;
            case ID_EIGHT:
                clickOnButton(DIGIT8);
                break;
            case ID_NINE:
                clickOnButton(DIGIT9);
                break;
            case ID_COMMA:
                clickOnButton(COMMA);
                break;
            case ID_MULTIPLICATION:
                clickOnButton(MULTIPLICATION);
                break;
            case SLASH_VAL:
                clickOnButton(DIVISION);
                break;
            case ID_SUBTRACTION:
                clickOnButton(SUBTRACTION);
                break;
            case ID_ADD:
                clickOnButton(ADD);
                break;
            case ID_SIGN:
                clickOnButton(SIGN);
                break;
            case ID_PERCENT:
                clickOnButton(PERCENT);
                break;
            case ID_EQUALS:
                clickOnButton(EQUALS);
                break;
            case ID_RESET:
                clickOnButton(ESCAPE);
                break;
            case ID_MC:
                clickOnButton(MC);
                break;
            case ID_MR:
                clickOnButton(MR);
                break;
            case ID_M_MINUS:
                clickOnButton(M_MINUS);
                break;
            case ID_M_PLUS:
                clickOnButton(M_PLUS);
                break;
            case ENTER_VAL:
                clickOnButton(EQUALS);
                break;


//            if (nameKey.equals(PERIOD_VAL)) { //TODO in CASE
//                return ID_COMMA;
//            }
//            if (nameKey.equals(C_VAL)) { //TODO in CASE
//                return ID_MC;
//            }
//            if (nameKey.equals(R_VAL)) {  //TODO in CASE
//                return ID_MR;
//            }
//            if (nameKey.equals(M_VAL)) { //TODO in CASE
//                return ID_M_MINUS;
//            }
//            if (nameKey.equals(P_VAL)) { //TODO in CASE
//                return ID_M_PLUS;
//            }

            default:
                break;
        }
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
        PauseTransition PAUSE = new PauseTransition(Duration.seconds(PAUSE_ANIMATION)); //TODO need fix!!!!!
        button.arm();
        button.fire();
        PAUSE.setOnFinished(e -> button.disarm());
        PAUSE.play();
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
    private boolean validateLimitNumberZeros() {
        String textDisplay = DISPLAY.getText();  //TODO -> возможно можно передать текст
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

    /**
     * The method determines the Shortcuts.
     *
     * @param event this event is generated when a key is pressed, released, or typed.
     * @return the id button.
     */
    private String shortcuts(KeyEvent event) {
        String nameKey = event.getCode().toString();
        String code = event.getText();

        if (code.equals(SEMICOLON_VAL) && nameKey.equals(ID_EIGHT)) {
            return ID_MULTIPLICATION;
        }
        if (code.equals(STAR_VAL) && nameKey.equals(ID_EIGHT)) {
            return ID_MULTIPLICATION;
        }
        if (code.equals(PLUS_VAL) && nameKey.equals(ID_EQUALS)) {
            return ID_ADD;
        }
        if (code.equals(UNDERSCORE_VAL) && nameKey.equals(MINUS_VAL)) {
            return ID_SIGN;
        }
        if (code.equals(SIGN_VAL) && nameKey.equals(MINUS_VAL)) {
            return ID_SUBTRACTION;
        }
        if (code.equals(COLON_VAL) && nameKey.equals(ID_FIVE)) {
            return ID_PERCENT;
        }
        if (code.equals(PERCENT_VAL) && nameKey.equals(ID_FIVE)) {
            return ID_PERCENT;
        }
//        if (nameKey.equals(SLASH_VAL)) { //TODO in CASE
//            return ID_DIVISION;
//        }
//        if (nameKey.equals(ENTER_VAL)) { //TODO in CASE
//            return ID_EQUALS;
//        }
        if (nameKey.equals(PERIOD_VAL)) { //TODO in CASE
            return ID_COMMA;
        }
        if (nameKey.equals(C_VAL)) { //TODO in CASE
            return ID_MC;
        }
        if (nameKey.equals(R_VAL)) {  //TODO in CASE
            return ID_MR;
        }
        if (nameKey.equals(M_VAL)) { //TODO in CASE
            return ID_M_MINUS;
        }
        if (nameKey.equals(P_VAL)) { //TODO in CASE
            return ID_M_PLUS;
        }
        return nameKey;
    }
}

