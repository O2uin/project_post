module com.example.tcp_client {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.tcp_client to javafx.fxml;
    exports com.example.tcp_client;
}