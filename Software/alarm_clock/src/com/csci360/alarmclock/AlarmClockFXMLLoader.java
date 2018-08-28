/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.csci360.alarmclock;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author Sarah
 */
public class AlarmClockFXMLLoader extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            AnchorPane root = new AnchorPane();

            root.getChildren().add(FXMLLoader.load(getClass().getResource("/AlarmClockUI.fxml")));

            Scene scene = new Scene(root, 650, 400);

            primaryStage.setTitle("Test Case's Alarm Clock");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
