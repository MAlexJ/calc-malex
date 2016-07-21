package com.malex;

import com.malex.controller.ViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import java.io.IOException;

public class MainApp extends Application {

    // инициализация логера
    private static Logger logger = Logger.getLogger(MainApp.class.getName());

    // хранить состояние Stage
    private Stage primaryStage;

    // точка запуска приложения
    public static void main(String[] args) {
        launch(args);
    }

    // This method is called on the JavaFX Application Thread.
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setResizable(false);
        mainView();
    }

    // инициализация контроллера и Stage
    private void mainView() {
        try {
            //Загрузка layout.fxml
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/view/layout.fxml"));
            AnchorPane anchorPane = loader.load();

            //Получение и инициализация контроллера
            ViewController controller = loader.getController();
            controller.init();

            // инициализация Scene
            this.primaryStage.setScene(new Scene(anchorPane));
            this.primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
    }
}
