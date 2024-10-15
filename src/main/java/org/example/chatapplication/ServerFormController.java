package org.example.chatapplication;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerFormController {
    public Button sendBtn;
    public JFXTextArea txtAreaMsgDisplay;
    public JFXButton btnSend;
    public JFXButton btnEmoji;
    public TextField txtMsg;

    private ServerSocket serverSocket;
    private Socket socket;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;
    private Thread listenerThread;

    public void initialize() {
        txtAreaMsgDisplay.setEditable(false);

        try {
            serverSocket = new ServerSocket(3001);

            socket = serverSocket.accept();
            System.out.println("Client accepted");

            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataInputStream = new DataInputStream(socket.getInputStream());

            listenerThread = new Thread(() -> {
                try {
                    String response;
                    while (true) {
                        response = dataInputStream.readUTF();
                        txtAreaMsgDisplay.appendText("Client: " + response + "\n");
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

    @FXML
    public void emojiBtnOnAction(ActionEvent event) {
        GridPane emojiGrid = new GridPane();
        emojiGrid.setHgap(5);
        emojiGrid.setVgap(5);

        String[] emojis = {"\uD83D\uDE01", "\uD83D\uDE02", "😍", "👍", "❤️", "😎", "😊", "😢", "😋", "🥺", "😱", "😜", "💪", "🙏", "💥", "🎉", "🎈"};

        int row = 0, col = 0;
        for (String emoji : emojis) {
            Button emojiBtn = new Button(emoji);
            emojiBtn.setStyle("-fx-font-size: 20px;");
            emojiBtn.setOnAction(e -> txtMsg.appendText(emoji));

            emojiGrid.add(emojiBtn, col, row);

            col++;
            if (col > 4) {
                col = 0;
                row++;
            }
        }

        Stage emojiStage = new Stage();
        emojiStage.initModality(Modality.APPLICATION_MODAL);
        emojiStage.setTitle("Choose an Emoji");
        emojiStage.setScene(new Scene(emojiGrid, 300, 300));
        emojiStage.show();
    }

}
