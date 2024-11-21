    package pl.kubag5.demo;

    import javafx.collections.FXCollections;
    import javafx.collections.ObservableList;
    import javafx.fxml.FXML;
    import javafx.scene.control.*;
    import javafx.scene.input.KeyEvent;
    import javafx.scene.layout.VBox;
    import javafx.scene.text.Text;
    import javafx.animation.TranslateTransition;
    import javafx.fxml.Initializable;
    import javafx.scene.layout.AnchorPane;
    import javafx.util.Duration;

    import java.net.URL;
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
        @FXML
        private Text MenuClose;

        // In the future: 'stationList' == stations from database
        private final ObservableList<String> stationList = FXCollections.observableArrayList(
                "Warszawa", "Kraków", "Wrocław", "Poznań", "Gdańsk", "Zakpopane", "Żyrardów", "Łódź", "Skierniewice", "Gdynia", "Kielce", "Warszawa Centralna", "Grodziks Mazowiecki"
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

    }
