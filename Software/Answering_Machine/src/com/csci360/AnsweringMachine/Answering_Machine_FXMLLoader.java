/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.csci360.AnsweringMachine;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author Sarah
 */
public class Answering_Machine_FXMLLoader extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        try{
        AnchorPane root = new AnchorPane();

        root.getChildren().add(FXMLLoader.load(getClass().getResource("/AnsweringMachineUI.fxml")));
        
        Scene scene = new Scene(root, 600, 300);
        
        primaryStage.setTitle("Sarah's Answering Machine");
        primaryStage.setScene(scene);
        primaryStage.show();
        }
        catch(Exception e){
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
