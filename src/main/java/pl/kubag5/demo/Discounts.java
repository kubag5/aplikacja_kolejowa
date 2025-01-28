package pl.kubag5.demo;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;

/**
 * Responsible for displaying list of available discounts
 * Uses the 'Discount' enum to retrieve discount amount
 * formats discounts, and add to the ListView.
 */
public class Discounts {
    @FXML
    public ListView<String> discountsListView;

    /**
     * Initializes the discounts controller
     * add items to 'discountsListView' using values form enum 'Discount'
     * This method is automatically called when the FXML file is loaded
     */
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
