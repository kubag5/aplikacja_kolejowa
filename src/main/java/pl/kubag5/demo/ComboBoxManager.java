package pl.kubag5.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.skin.ComboBoxListViewSkin;
import javafx.util.StringConverter;

/**
 * Manages the behavior of a ComboBox, allowing for editable functionality,
 * filtering of items based on user input, dropdown height adjustment, and input validation.
 * It ensures that the ComboBox behaves dynamically and provides a smooth user experience.
 */
public class ComboBoxManager {

    private final ComboBox<String> comboBox;
    private final ObservableList<String> itemsList;

    /**
     * Constructor to initialize ComboBoxManager
     *
     * @param comboBox - Element(ComboBox) to be managed
     * @param list - The list of items to be displayed
     */
    public ComboBoxManager(ComboBox<String> comboBox, ObservableList<String> list) {
        this.comboBox = comboBox;
        this.itemsList = list;

        this.comboBox.setItems(FXCollections.observableArrayList(itemsList));
        init();
    }

    /**
     * Initializes the ComboBox
     * Causes executions of: dropdown list filtration,
     * adjustment of the dropdown list height and adjustment of the list contents
     */
    private void init() {
        comboBox.setEditable(true);
        setDropdownHeight(6);
        listFilter();
        validation();

    }

    /**
     * Filtering of the ComboBox dropdown list contest according to the user`s input
     * List is updated with each keystroke
     * List is filtered to only show those elements that match the input
     */
    private void listFilter() {
        TextField editor = comboBox.getEditor();
        editor.textProperty().addListener((obs, oldValue, newValue) -> {
            if (oldValue == null || newValue.isEmpty()) {
                comboBox.setItems(FXCollections.observableArrayList(itemsList));
            } else {
                ObservableList<String> filteredList = FXCollections.observableArrayList();
                for (String item : itemsList) {
                    if (item.toLowerCase().contains(newValue.toLowerCase())) {
                        filteredList.add(item);
                    }
                }
                comboBox.setItems(filteredList);
            };
            if (!comboBox.getItems().isEmpty()) {
                comboBox.show();
            }
        });
    }

    /**
     * Sets the height of the dropdown list
     * @param maxHeight - maximum amount of visible list items when dropdown list is fully extended
     */
    private void setDropdownHeight(int maxHeight) {
        comboBox.skinProperty().addListener((obs, oldSkin, newSkin) -> {
            if (newSkin instanceof ComboBoxListViewSkin) {
                ListView<?> listView = (ListView<?>) ((ComboBoxListViewSkin<?>) newSkin).getPopupContent();
                listView.setFixedCellSize(30);
                listView.setMaxHeight(maxHeight * 30);
            }
        });
    }

    /**
     * Adds input validation to the ComboBox. The validation ensures that the input matches a valid item from the list
     * If the user enters an invalid item, the ComboBox will be cleared.
     */
    private void validation() {

        TextField editor = comboBox.getEditor();
        editor.focusedProperty().addListener((obs, wasFocused, isFocused) -> {
            if (!isFocused) {
                validateInput();
            }
        });

        comboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(String s) {
                return s != null ? s : "";
            }

            @Override
            public String fromString(String string) {
                if (itemsList.stream().anyMatch(item -> item.equals(string))) {
                    return itemsList.stream().filter(item -> item.equals(string)).findFirst().orElse(null);
                }
                return null;
            }
        });
    }

    /**
     * Validates the user input
     * If the input does not match any item in the list the ComboBox value is cleared
     * If the input matches an item, it sets that item as that value
     */
    private void validateInput() {
        String input = comboBox.getEditor().getText();
        if (itemsList.stream().noneMatch(item -> item.equals(input))) {
            comboBox.setValue(null);
        } else {
            comboBox.setValue(itemsList.stream().filter(item -> item.equals(input)).findFirst().orElse(null));
        }
    }

}
