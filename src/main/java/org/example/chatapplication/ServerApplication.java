package org.example.chatapplication;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ServerApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("server_form.fxml"));
        primaryStage.setScene(new Scene(loader.load()));
        primaryStage.setTitle("Server");

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}