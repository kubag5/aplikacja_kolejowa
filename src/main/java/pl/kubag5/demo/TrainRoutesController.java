package pl.kubag5.demo;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Controller class for managing train routes
 * class uses JavaFX to display train routes in a TableView,
 */
public class TrainRoutesController {

    @FXML
    private TableView<Route> routesTable;

    @FXML
    private TableColumn<Route, Integer> routeIdColumn;

    @FXML
    private TableColumn<Route, String> startStationColumn;

    @FXML
    private TableColumn<Route, String> endStationColumn;

    @FXML
    private TableColumn<Route, String> departureTimeColumn;

    @FXML
    private TableColumn<Route, String> arrivalTimeColumn;

    private final ObservableList<Route> routes = FXCollections.observableArrayList();

    /**
     * Initializes the table and binds data to its columns
     * This method is automatically called when the FXML loaded
     */
    @FXML
    public void initialize() {
        routeIdColumn.setCellValueFactory(new PropertyValueFactory<>("routeId"));
        startStationColumn.setCellValueFactory(new PropertyValueFactory<>("startStation"));
        endStationColumn.setCellValueFactory(new PropertyValueFactory<>("endStation"));
        departureTimeColumn.setCellValueFactory(new PropertyValueFactory<>("departureTime"));
        arrivalTimeColumn.setCellValueFactory(new PropertyValueFactory<>("arrivalTime"));

        routesTable.setItems(routes);
    }

    /**
     * adds a new route to the table
     *
     * @param routeId - identifier of a route
     * @param startStation - name of starting station
     * @param endStation - name of ending station
     * @param departureTime - time of departure
     * @param arrivalTime - time of arrival
     */
    public void addRoute(int routeId, String startStation, String endStation, String departureTime, String arrivalTime) {
        routes.add(new Route(routeId, startStation, endStation, departureTime, arrivalTime));
    }

}
