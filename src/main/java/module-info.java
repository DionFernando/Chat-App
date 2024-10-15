module org.example.chatapplication {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.jfoenix;


    opens org.example.chatapplication to javafx.fxml;
    exports org.example.chatapplication;
}