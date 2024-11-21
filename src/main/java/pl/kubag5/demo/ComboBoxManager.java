package pl.kubag5.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.skin.ComboBoxListViewSkin;
import javafx.util.StringConverter;


public class ComboBoxManager {

    private final ComboBox<String> comboBox;
    private final ObservableList<String> itemsList;

    public ComboBoxManager(ComboBox<String> comboBox, ObservableList<String> list) {
        this.comboBox = comboBox;
        this.itemsList = list;

        this.comboBox.setItems(FXCollections.observableArrayList(itemsList));
        init();
    }

    private void init() {
        comboBox.setEditable(true);
        setDropdownHeight();
        listFilter();
        validation();

    }

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

    private void setDropdownHeight() {
        comboBox.skinProperty().addListener((obs, oldSkin, newSkin) -> {
            if (newSkin instanceof ComboBoxListViewSkin) {
                ListView<?> listView = (ListView<?>) ((ComboBoxListViewSkin<?>) newSkin).getPopupContent();
                listView.setFixedCellSize(30);
                listView.setMaxHeight(6 * 30);
            }
        });
    }

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
    private void validateInput() {
        String input = comboBox.getEditor().getText();
        if (itemsList.stream().noneMatch(item -> item.equals(input))) {
//            comboBox.getEditor().setText("");
            comboBox.setValue(null);
        } else {
            comboBox.setValue(itemsList.stream().filter(item -> item.equals(input)).findFirst().orElse(null));
        }
    }

}
