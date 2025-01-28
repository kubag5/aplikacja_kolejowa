package pl.kubag5.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The main class for train application
 * Initializes and lunches the application by loading FXML file and applying .css file
 */
public class HelloApplication extends Application {

    /**
     * Initializes the application by loading FXML file and applying .css file
     * @param stage - primary stage for application
     * @throws IOException - is there in case of an FXML loading issue
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 568);
        scene.getStylesheets().add(getClass().getResource("/css/HelloApplicationStyle.css").toExternalForm());
        stage.setTitle("Aplikacja kolejowa");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Launches the application
     * @param args - arguments passed by application
     */
    public static void main(String[] args) {
        launch();
    }
}