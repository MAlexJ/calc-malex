package com.malex;

import com.malex.controller.ViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * The {@code MainApp} class  extends the {@code Application} class and contains two methods: 'start' and 'main'.
 * <br>
 * This basic structure, which is required to run JavaFX applications.
 * <p>
 * The {@code start(Stage primaryStage)} method  is automatically called when calling launch(...) of the main a method.
 * <br>
 * The {@code main(String[] args)} method is designed to run the program.
 * <p>
 * Here are some more examples of how this can be used:
 * <pre>
 * // Step #1: Declare the stage: {@code Stage}.
 * private Stage primaryStage;
 *
 * // Step #2: Initialization the application, the attachment stage: {@code Stage}.
 * public void start(Stage primaryStage) {
 *  this.primaryStage = primaryStage;
 *  this.primaryStage.setResizable(false);
 * }
 *
 * // Step #3: Create method to run the application.
 * public static void main(String[] args) {
 *  launch(args);
 * }
 * </pre>
 *
 * @author MAlex
 * @see com.malex.controller.ViewController
 * @see javafx.application.Application
 * @see javafx.fxml.FXMLLoader
 * @see javafx.scene.Scene
 * @see javafx.scene.layout.AnchorPane
 * @see javafx.stage.Stage
 * @see org.apache.log4j.Logger
 */
public class MainApp extends Application {

    /**
     * Logging initialization.
     */
    private static Logger logger = Logger.getLogger(MainApp.class.getName());

    /**
     * Value is used to store the stage.
     */
    private Stage primaryStage;

    /**
     * Application start point.
     *
     * @param args array of the values.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * This method is called on the JavaFX Application Thread.
     *
     * @param primaryStage value of the stage.
     */
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setResizable(false);
        mainView();
    }

    /**
     * Initialization the controller, the primary stage and load fxml file.
     */
    private void mainView() {
        try {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/view/layout.fxml"));
            AnchorPane anchorPane = loader.load();

            ViewController controller = loader.getController();
            controller.init();

            this.primaryStage.setScene(new Scene(anchorPane));
            this.primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("Error -> mainView()" + e.getMessage());
        }
    }
}
