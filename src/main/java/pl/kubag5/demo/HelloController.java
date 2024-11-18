    package pl.kubag5.demo;

    import javafx.fxml.FXML;
    import javafx.scene.control.TextField;
    import javafx.scene.input.KeyCode;
    import javafx.scene.input.KeyEvent;
    import javafx.scene.text.Text;

    public class HelloController {
        @FXML
        public Text timeHintText;
        @FXML
        public TextField timeTextField;
        @FXML
        private Text departureHintText;
        @FXML
        private TextField departureTextField;
        @FXML
        public TextField arrivalTextField;
        @FXML
        public Text arrivalHintText;


        @FXML
        public void removeHint(KeyEvent event) {
            Object source = event.getSource();

            if (source instanceof TextField) {
                TextField textField = (TextField) source;
                Text hintText = getHintFor(textField);

                if (hintText != null) {
                    hintText.setVisible(textField.getText().isEmpty());
                }
            }
        }

        private Text getHintFor(TextField textField) {
            return switch (textField.getId()) {
                case "departureTextField" -> departureHintText;
                case "arrivalTextField" -> arrivalHintText;
                case "timeTextField" -> timeHintText;
                default -> null;
            };
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
