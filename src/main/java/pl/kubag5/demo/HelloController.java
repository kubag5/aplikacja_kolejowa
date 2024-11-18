    package pl.kubag5.demo;

    import javafx.fxml.FXML;
    import javafx.scene.control.TextField;
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
    }
