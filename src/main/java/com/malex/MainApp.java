package com.malex;

import com.malex.controller.ViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {

    private Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setResizable(false);
        mainView();
    }

    private void mainView() {
        try {
            //Загрузка layout.fxml
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/view/layout.fxml"));
            AnchorPane anchorPane = loader.load();

            //Получение контроллера
            ViewController controller = loader.getController();
            controller.init();

            primaryStage.setScene(new Scene(anchorPane));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
