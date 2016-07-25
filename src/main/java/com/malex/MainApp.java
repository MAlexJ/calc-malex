package com.malex;

import com.malex.controller.ViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

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
 * // Step #1: Declare the stage.
 * private Stage primaryStage;
 *
 * // Step #2: Create method to run the application.
 * public static void main(String[] args) {
 *  launch(args);
 * }
 *
 * // Step #3: Initialization the application, the attachment stage {@code Stage)}.
 * public void start(Stage primaryStage) {
 *  this.primaryStage = primaryStage;
 *  this.primaryStage.setResizable(false);
 * }
 * </pre>
 *
 * @author MAlex
 * @see com.malex.controller.ViewController
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
        URL iconURL = MainApp.class.getResource("/img/calculator-icon.png");
        Image image = new ImageIcon(iconURL).getImage();
        com.apple.eawt.Application.getApplication().setDockIconImage(image);

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
        this.primaryStage.initStyle(StageStyle.TRANSPARENT);
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
            anchorPane.setBackground(Background.EMPTY);

            ViewController controller = loader.getController();
            controller.init();

            Scene scene = new Scene(anchorPane);
            scene.setFill(Color.TRANSPARENT);

            this.primaryStage.setScene(scene);
            this.primaryStage.show();
        } catch (Exception e) {
            logger.error("Error -> mainView()" + e.getMessage());
        }
    }
}
