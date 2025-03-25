    package pl.kubag5.demo;

    import javafx.collections.FXCollections;
    import javafx.collections.ObservableList;
    import javafx.fxml.FXML;
    import javafx.fxml.FXMLLoader;
    import javafx.scene.Parent;
    import javafx.scene.Scene;
    import javafx.scene.control.*;
    import javafx.scene.input.KeyEvent;
    import javafx.scene.layout.HBox;
    import javafx.scene.layout.VBox;
    import javafx.scene.text.Text;
    import javafx.animation.TranslateTransition;
    import javafx.fxml.Initializable;
    import javafx.scene.layout.AnchorPane;
    import javafx.stage.Stage;
    import javafx.util.Duration;

    import java.net.URL;
    import java.sql.*;
    import java.time.LocalDate;
    import java.util.ResourceBundle;

    /**
     * Controller class for the Hello application that manages the UI behavior and handles events.
     * It interacts with the database and provides functionalities like train search, time formatting, and menu toggling.
     */
    public class HelloController implements Initializable {
        @FXML
        public TextField timeTextField;
        public VBox sideMenu;
        public AnchorPane menuButton;
        public VBox menuBox;
        public AnchorPane menuContainer;
        public DatePicker datePicker;
        public AnchorPane reverseLineSearch;
        public ComboBox<String> departueComboBox;
        public ComboBox<String> arrivalComboBox;
        public HBox discountsInfo;

        ComboBoxManager departureComboBoxManager;
        ComboBoxManager arrivalComboBoxManager;


        /**
         * Initializes the controller for all interactive UI elements from according FXML file
         * It also handles onclick events
         *
         * @param url - location of a FXML file
         * @param resourceBundle - resource bundle
         */
        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
            departureComboBoxManager = new ComboBoxManager(departueComboBox, arrivalComboBox.getEditor().getText());
            arrivalComboBoxManager = new ComboBoxManager(arrivalComboBox, departueComboBox.getEditor().getText());

            datePicker.setValue(LocalDate.now());

            sideMenu.setTranslateX(-300);
            menuContainer.setPrefWidth(0);
            sideMenu.setVisible(false);

            /**
             * Open discounts with window
             */
            discountsInfo.setOnMouseClicked(event -> {
                try {
                    FXMLLoader fxmlLoader =  new FXMLLoader(HelloApplication.class.getResource("discounts.fxml"));
                    Parent root = fxmlLoader.load();

                    Stage stage = new Stage();
                    stage.setTitle("Lista Zniżek");
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });


            /**
             * Handle the menu button click to toggle the side menu visibility
             */
            menuButton.setOnMouseClicked(mouseEvent -> {
                TranslateTransition slide = new TranslateTransition(Duration.seconds(.4), sideMenu);
                if(sideMenu.isVisible()) {
                    slide.setToX(-300);
                    slide.play();
                    slide.setOnFinished(event -> {
                        sideMenu.setVisible(!sideMenu.isVisible());
                        menuContainer.setPrefWidth(0);
                    });
                } else {
                    menuContainer.setPrefWidth(300);

                    slide.setToX(0);
                    sideMenu.setVisible(!sideMenu.isVisible());
                    slide.play();
                }
            });

            /**
             * Handle switching contents between TextFields containing departure and arrival stations
             */
            reverseLineSearch.setOnMouseClicked(event -> {
                String temp = departueComboBox.getEditor().getText();
                departueComboBox.getEditor().setText(arrivalComboBox.getEditor().getText());
                arrivalComboBox.getEditor().setText(temp);
            });
        }


        /**
         * Formats the time input in the 'timeTextField'. This method ensures the input is in HH:mm format.
         * It adjusts the user input to ensure it fits within valid time constraints
         * if user input is outside of those boundaries time is automatically adjusted (max 23 hours, 59 minutes)
         * @param event - triggered when a key is pressed while focused on the timeTextField - helps to identify event
         */
        @FXML
        public void formatTime(KeyEvent event) {
            String s = timeTextField.getText();

            s = s.replaceAll("[^\\d:]", "");

            if (s.contains(":") && s.indexOf(':') != 2) {
                s = s.replace(":", "");
            }

            if (s.length() == 2) {
                int hours = Integer.parseInt(s);
                if (hours > 23) {
                    hours = 23;
                }
                s = String.format("%02d:", hours);
            }


            if (s.length() > 5) {
                s = s.substring(0, 5);
            }

            if (s.length() == 5) {
                String[] parts = s.split(":");
                int hours = Integer.parseInt(parts[0]);
                int minutes = Integer.parseInt(parts[1]);

                if (hours > 23) {
                    hours = 23;
                }
                if (minutes > 59) {
                    minutes = 59;
                }

                s = String.format("%02d:%02d", hours, minutes);
            }

            timeTextField.setText(s);
            timeTextField.positionCaret(s.length());
        }


        /**
         * Searches for available train routes from the database. It connects to the database using JDBC, retrieves
         * routes based on the selected departure and arrival stations, and displays them in a new window.
         * Currently, it fetches all routes without applying any filters.
         */
        @FXML
        public void searchTrains() {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("trainRoutes.fxml"));
                Parent root = loader.load();

                TrainRoutesController routesController = loader.getController();

                try (ResultSet resultSet = Database.statement.executeQuery("SELECT route_id, start_station, end_station, end_time, start_time FROM trains")) {

                    while (resultSet.next()) {
                        int routeId = resultSet.getInt("route_id");
                        String startStation = resultSet.getString("start_station");
                        String endStation = resultSet.getString("end_station");
                        String departureTime = resultSet.getString("end_time");
                        String arrivalTime = resultSet.getString("start_time");

                        routesController.addRoute(routeId, startStation, endStation, departureTime, arrivalTime);
                    }
                }

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Możliwe Połączenia:");
                stage.show();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
