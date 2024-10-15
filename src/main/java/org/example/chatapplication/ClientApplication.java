package org.example.chatapplication;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ClientApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("client_form.fxml"));
        primaryStage.setScene(new Scene(loader.load()));
        primaryStage.setTitle("Client");


        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}