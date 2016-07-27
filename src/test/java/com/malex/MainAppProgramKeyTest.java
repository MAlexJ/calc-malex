package com.malex;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.util.concurrent.CountDownLatch;

import static junit.framework.TestCase.assertEquals;

/**
 * https://community.oracle.com/thread/2257559?tstart=0
 */

public class MainAppProgramKeyTest extends ApplicationTest {

    @Override
    public void start(Stage stage) throws Exception {
        //none
    }

    @Test
    public void test() throws InterruptedException {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        Platform.runLater(() -> {
            MainApp mainApp = new MainApp();
            Stage stage = new Stage();
            mainApp.start(stage);
            Scene scene = stage.getScene();

            // ->>>> start test
            Button btn2 = (Button) scene.lookup("#DIGIT2");
            btn2.fire();
            btn2.fire();
            btn2.fire();
            btn2.fire();
            btn2.fire();


            TextField DISPLAY = (TextField) scene.lookup("#DISPLAY");
            assertEquals("22222", DISPLAY.getText());
            // ->>>>  end test

            countDownLatch.countDown();
        });
        countDownLatch.await();
    }
}
