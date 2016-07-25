package com.malex;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

public class MainAppTest extends ApplicationTest {

    private Scene sceneRoot;

    @Override
    public void start(Stage stage) throws Exception {
        MainApp mainApp = new MainApp();
        mainApp.start(stage);
        sceneRoot = stage.getScene();

    }

    @Test
    public void test() {


    }
}
