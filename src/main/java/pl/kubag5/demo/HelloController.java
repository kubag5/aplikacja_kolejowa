    package pl.kubag5.demo;

    import javafx.animation.KeyFrame;
    import javafx.animation.Timeline;
    import javafx.fxml.FXML;
    import javafx.scene.control.Button;
    import javafx.scene.control.DatePicker;
    import javafx.scene.control.Label;
    import javafx.scene.control.TextField;
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
        @FXML
        private Text MenuClose;
        @FXML
        private TextField departureTextField;
        @FXML
        public TextField arrivalTextField;


        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {

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
                String temp = departureTextField.getText();
                departureTextField.setText(arrivalTextField.getText());
                arrivalTextField.setText(temp);
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
