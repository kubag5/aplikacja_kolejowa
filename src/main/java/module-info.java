module pl.kubag5.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;
    requires org.jsoup;

    opens pl.kubag5.demo to javafx.fxml;
    exports pl.kubag5.demo;
}