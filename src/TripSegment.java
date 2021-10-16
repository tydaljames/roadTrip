import java.util.List;

public class TripSegment {

    /**
     *
     */
    List<City> route;

    /**
     * The starting city for the given trip segment.
     */
    City startCity;

    /**
     * The ending city for the given route.
     */
    City endCity;

    /**
     * The total time in minutes between startCity and endCity for this Path.
     *
     * The total distance between
     */
    int totalMinutes;


    int totalMiles;


    public TripSegment(List<City> route, int totalMinutes, int totalMiles, City startCity, City endCity){
        this.route = route;
        this.totalMinutes = totalMinutes;
        this.totalMiles = totalMiles;
        this.startCity = startCity;
        this.endCity = endCity;
    }

    @Override
    public String toString() {
        return "Path for " + startCity + " to " + endCity + " -- " +
                "Minutes spent: " + totalMinutes + ", " +
                "Miles driven: " + totalMiles +
                "\n" + route;
    }
}
