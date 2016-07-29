package com.malex;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;

import static junit.framework.TestCase.assertEquals;

/**
 * https://community.oracle.com/thread/2257559?tstart=0
 */

public class MainAppProgramKeyTest {
//
//    private static Stage STAGE;
//    private static TextField DISPLAY;
//    private static Button B2;
//    private static Button ADD;
//    private static Button EQUALS;
//    private static Button ESCAPE;
//
//    static {
//        new JFXPanel();
//    }
//
//    @BeforeClass
//    public static void setUp() throws InterruptedException {
//        final CountDownLatch countDownLatch = new CountDownLatch(1);
//        Platform.runLater(() -> {
//            STAGE = new Stage();
//            new MainApp().start(STAGE);
//            Scene scene = STAGE.getScene();
//
//            B2 = (Button) scene.lookup("#DIGIT2");
//            ADD = (Button) scene.lookup("#ADD");
//            EQUALS = (Button) scene.lookup("#EQUALS");
//            DISPLAY = (TextField) scene.lookup("#DISPLAY");
//            ESCAPE = (Button) scene.lookup("#ESCAPE");
//
//            countDownLatch.countDown();
//        });
//        countDownLatch.await();
//    }
//
//    @Test
//    public void test() {
//        ESCAPE.fire();
//        B2.fire();
//        B2.fire();
//        B2.fire();
//        B2.fire();
//        ADD.fire();
//        B2.fire();
//        B2.fire();
//        EQUALS.fire();
//
//        assertEquals("2244", DISPLAY.getText());
//    }
//
//    @Test
//    public void test2() {
//        ESCAPE.fire();
//        B2.fire();
//        B2.fire();
//        B2.fire();
//        B2.fire();
//        B2.fire();
//        B2.fire();
//        B2.fire();
//        B2.fire();
//        B2.fire();
//        B2.fire();
//        B2.fire();
//        ADD.fire();
//        B2.fire();
//        B2.fire();
//        B2.fire();
//        EQUALS.fire();
//
//        assertEquals("22222222444", DISPLAY.getText());
//    }
//
//    @AfterClass
//    public static void close() {
//        Platform.exit();
//    }

}
