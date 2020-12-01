import java.util.List;

public class path {
    List<destination> route;
    int total_minutes;
    int total_miles;
    destination start_city;
    destination end_city;


    public path(List<destination> route, int total_minutes, int total_miles, destination start_city, destination end_city){
        this.route = route;
        this.total_minutes = total_minutes;
        this.total_miles = total_miles;
        this.start_city = start_city;
        this.end_city = end_city;
    }

    @Override
    public String toString() {
        return "Path for " + start_city + " to " + end_city + " -- " +
                "Minutes spent: " + total_minutes + ", " +
                "Miles driven: " + total_miles +
                "\n" + route;
    }
}
