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
    import java.sql.Connection;
    import java.sql.DriverManager;
    import java.sql.ResultSet;
    import java.sql.Statement;
    import java.time.LocalDate;
    import java.util.ResourceBundle;

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

        @FXML
        private Text MenuClose;

        // In the future: 'stationList' == stations from database
        private final ObservableList<String> stationList = FXCollections.observableArrayList(
                "Warszawa", "Kraków", "Wrocław", "Poznań", "Gdańsk", "Zakopane", "Żyrardów", "Łódź", "Skierniewice", "Gdynia", "Kielce", "Warszawa Centralna", "Grodzisk Mazowiecki"
        );
        ComboBoxManager departureComboBoxManager;
        ComboBoxManager arrivalComboBoxManager;


        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
            departureComboBoxManager = new ComboBoxManager(departueComboBox, stationList);
            arrivalComboBoxManager = new ComboBoxManager(arrivalComboBox, stationList);


            datePicker.setValue(LocalDate.now());


            sideMenu.setTranslateX(-300);
            menuContainer.setPrefWidth(0);
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

            sideMenu.setVisible(false);

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

            reverseLineSearch.setOnMouseClicked(event -> {
                String temp = departueComboBox.getEditor().getText();
                departueComboBox.getEditor().setText(arrivalComboBox.getEditor().getText());
                arrivalComboBox.getEditor().setText(temp);
            });
        }



        //TODO: when the time is only partially given, '0' should be inserted in the empty spaces
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

        // funkcja pobierająca możliwe połączenia pociągów, póki co bez filtrów wyszukiwania. Funkcja korzysta z JConnector do połączeń z bazą danych. Wykorzystuje protokół JDBC. Aktualnie funkcja pobiera: stacja początkową, stacja końcową, czas odjazdu, czas przyjazdu
        @FXML
        public void searchTrains() {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("trainRoutes.fxml"));
                Parent root = loader.load();

                TrainRoutesController routesController = loader.getController();



                try (Connection connection = DriverManager.getConnection(Database.dbUrl, Database.dbUser, Database.dbPassword);
                     Statement statement = connection.createStatement();
                     ResultSet resultSet = statement.executeQuery("SELECT route_id, start_station, end_station, end_time, start_time FROM trains")) {

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
