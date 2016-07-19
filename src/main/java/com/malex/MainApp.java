package com.malex;

import com.malex.controller.ViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {

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
            primaryStage.setScene(new Scene(anchorPane));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
