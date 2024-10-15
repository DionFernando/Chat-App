package org.example.chatapplication;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.WindowEvent;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientFormController {
    public Button sendButton;
    public JFXTextArea txtAreaMsgDisplay;
    public JFXButton btnSend;
    public TextField txtMsg;
    public JFXButton btnEmoji;

    private Socket socket;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;
    private Thread listenerThread;

    public void initialize() {
        txtAreaMsgDisplay.setEditable(false);
        try {
            socket = new Socket("localhost", 3001);
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataInputStream = new DataInputStream(socket.getInputStream());

            listenerThread = new Thread(() -> {
                try {
                    String response;
                    while (true) {
                        // receive the data
                        response = dataInputStream.readUTF();
                        txtAreaMsgDisplay.appendText("Server: " + response + "\n");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            listenerThread.start();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void sendBtnOnAction(ActionEvent event) {
        try {
            String msg = txtMsg.getText();
            txtAreaMsgDisplay.appendText("Me: " + msg + "\n");
            dataOutputStream.writeUTF(msg);
            dataOutputStream.flush();

            txtMsg.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}