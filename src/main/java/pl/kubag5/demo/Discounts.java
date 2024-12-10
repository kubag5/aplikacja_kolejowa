package pl.kubag5.demo;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class Discounts {
    @FXML
    public ListView<String> discountsListView;

    @FXML
    public void initialize() {
        for (Discount discount : Discount.values()) {
            String listItem = String.format("%s - %.0f%% zniżki (%s)",
                    discount.name(),
                    discount.getDiscount() * 100,
                    discount.getDescription());
            discountsListView.getItems().add(listItem);
        }
    }
}
