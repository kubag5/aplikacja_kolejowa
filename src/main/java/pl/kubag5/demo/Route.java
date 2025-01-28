package pl.kubag5.demo;
// Klasa "Trasy" pociągu. Docelowo będzie zawierać wszystkie informacje o trasie pociągu.
public class Route {

    // pola
    private final int routeId;
    private final String startStation;
    private final String endStation;
    private final String departureTime;
    private final String arrivalTime;

    // konstruktor
    public Route(int routeId, String startStation, String endStation, String departureTime, String arrivalTime) {
        this.routeId = routeId;
        this.startStation = startStation;
        this.endStation = endStation;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }

    // gettery
    public int getRouteId() {
        return routeId;
    }

    public String getStartStation() {
        return startStation;
    }

    public String getEndStation() {
        return endStation;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }
}
