package com.malex;

import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import org.junit.BeforeClass;
import org.junit.Test;
import org.loadui.testfx.GuiTest;
import org.loadui.testfx.utils.FXTestUtils;

import java.awt.*;
import java.awt.event.InputEvent;
import java.util.concurrent.CountDownLatch;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class TrayAndMoveAppTest {

    private static GuiTest controller;

    @BeforeClass
    public static void setUpClass() throws InterruptedException {
        FXTestUtils.launchApp(MainApp.class);
        controller = new GuiTest() {
            @Override
            protected Parent getRootNode() {
                return stage.getScene().getRoot();
            }
        };
    }

    @Test
    public void testAppMove() {
        //given
        controller.push(KeyCode.ESCAPE);
        controller.click("#display");

        //when
        clickMouse(140, 200);
        controller.sleep(500);
        clickMouse(900, 200);
        controller.sleep(500);
        clickMouse(600, 400);
        controller.sleep(500);
        testTray();
    }

    private void testTray() {
        //STEP #1 find button
        Button tray = FXTestUtils.getOrFail("#tray");
        assertFalse(tray.isHover());

        //STEP #2 press button
        controller.click("#tray");
        controller.sleep(500);

        //STEP #3 check button
        assertTrue(tray.isHover());
    }

    private void clickMouse(int positionX, int positionY) {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        Platform.runLater(() -> {
            try {
                Robot robot = new Robot();
                robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                robot.mouseMove(positionX, positionY);
            } catch (Exception ignored) {
                //IGNORE
            }
            countDownLatch.countDown();
        });
        try {
            countDownLatch.await();
        } catch (Exception e) {
            //Ignore
        }
    }
}
