
import java.io.IOException;

import java.util.List;


public class RoadTrip {

    public static void main(String[] args) throws IOException {
        DataHolder dh = new DataHolder(args);
        UserInterface ui = new UserInterface(dh);
        ShortestPathFinder dijkstra = new ShortestPathFinder(dh.getCities(),dh.getRoads());

        ui.userInput();

        List<TripSegment> tripSegments = (dijkstra.route(ui.getStartCity(), ui.getEndCity(), dh.getAttractions()));

        for(TripSegment tripSegment : tripSegments){
            if(tripSegment != null){
                System.out.println(tripSegment);
            }
        }
    }
}
