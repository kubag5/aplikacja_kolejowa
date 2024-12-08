module pl.kubag5.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.poi.poi;
    requires org.jsoup;
    requires org.apache.poi.ooxml;


    opens pl.kubag5.demo to javafx.fxml;
    exports pl.kubag5.demo;
}