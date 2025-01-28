module pl.kubag5.demo {
    requires javafx.controls;
    requires javafx.fxml;
<<<<<<< HEAD
    requires java.sql;
=======
    requires org.apache.poi.poi;
    requires org.jsoup;
    requires org.apache.poi.ooxml;
>>>>>>> feature/add-records


    opens pl.kubag5.demo to javafx.fxml;
    exports pl.kubag5.demo;
}