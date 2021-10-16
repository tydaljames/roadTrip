import java.util.List;

public class Path {

    /**
     *
     */
    List<City> route;

    /**
     * The starting city for the given route.
     *
     * Note: Routes are two-directional. A path may go from endCity to StartCity, and vice versa.
     */
    City startCity;

    /**
     * The ending city for the given route.
     *
     * Note: Routes are two-directional. A path may go from endCity to StartCity, and vice versa.
     */
    City endCity;

    /**
     * The total time in minutes between startCity and endCity for this Path.
     *
     * The total distance between
     */
    int totalMinutes;


    int totalMiles;





    public Path(List<City> route, int totalMinutes, int totalMiles, City startCity, City endCity){
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
