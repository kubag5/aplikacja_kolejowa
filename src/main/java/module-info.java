module pl.kubag5.demo {
    requires javafx.controls;
    requires javafx.fxml;


    opens pl.kubag5.demo to javafx.fxml;
    exports pl.kubag5.demo;
}