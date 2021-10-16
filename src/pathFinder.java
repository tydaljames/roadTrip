import java.util.*;

public class pathFinder {
    private List<City> Cities;
    private List<road> roads;
    private Map<City, City> predecessors;
    private Map<City, Integer> distance;
    private Set<City> known;
    private Set<City> unknown;

    public pathFinder(ArrayList<City> Cities, ArrayList<road> roads) {
        this.Cities = Cities;
        this.roads = roads;
    }


        /*Sets distances to destinations using Dijkstra's algorithm*/
    public void dijkstras(City start_city) {
        predecessors = new HashMap<>();
        distance = new HashMap<>();
        known = new HashSet<>();
        unknown = new HashSet<>();

        distance.put(start_city, 0);
        unknown.add(start_city);
        while (unknown.size() > 0) {
            City d = findLowest(unknown);
            known.add(d);
            unknown.remove(d);
            findShortestPath(d);
        }
    }


        /*Uses distances set by Dijkstra's Algorithm to find the shortest path to end_city*/
    public Path findPath(City start_city, City end_city) {
        int total_minutes = 0;
        int total_miles = 0;
        List<City> route = new ArrayList<>();

        City cur = end_city;
        if (predecessors.get(cur) == null) {
            return null;
        }
        route.add(cur);
        while (predecessors.get(cur) != null) {
            cur = predecessors.get(cur);
            route.add(cur);
        }

        Collections.reverse(route);

        for (int i = 1; i < route.size(); i++) {
            total_minutes += findDistance(route.get(i - 1), route.get(i));
            total_miles += findMiles(route.get(i - 1), route.get(i));
        }

        /*Path object is created containing all data on the current start_city to end_city path segment*/
        Path newpath = new Path(route, total_minutes, total_miles, start_city, end_city);
        return newpath;
    }

    /*Runs all subfunctions and delivers the returns the resulting list of paths*/
    public List<Path> route(String starting_city, String ending_city, List<String> attractions) {
        City start = null;
        City end = null;
        List<City> attractionList = new ArrayList<>();
        List<Path> Paths = new ArrayList<>();


            /*Converts strings to destination objects for later use*/
        for (City d : Cities) {
            if (d.getCityName().equalsIgnoreCase(starting_city)) {
                start = d;
            }
            if (d.getCityName().equalsIgnoreCase(ending_city)) {
                end = d;
            }

            for (String s : attractions) {
                for(String a : d.getAttractionsList()) {
                    if (a.equalsIgnoreCase(s)) {
                        attractionList.add(d);
                    }
                }
            }
        }

        dijkstras(start);
        City nearest = start;

        while(attractionList.size() > 0){
            nearest = nearestAttraction(attractionList);
            attractionList.remove(nearest);
            Paths.add(findPath(start, nearest));
            start = nearest;
            dijkstras(start);
        }

        Paths.add(findPath(nearest,end));
        return Paths;
    }

            /*Finds the nearest attraction to our current node based on the current values in the distance map*/
        private City nearestAttraction(List<City> attractionList) {
            City nearest = null;
            int pathWeight = Integer.MAX_VALUE;

            for(City a : attractionList){
                int i = distance.get(a);
                if (i < pathWeight) {
                    pathWeight = distance.get(a);
                    nearest = a;
                }
        }
          return nearest;
    }

        /*Finds the lowest value unknown node for Dijkstra's Algorithm*/
    private City findLowest(Set<City> Cities){
        City lowest = null;
        for(City d : Cities){
            if(lowest == null || (shortestDistance(d) < shortestDistance(lowest))  ){
                lowest = d;
            }
        }
        return lowest;
    }

            /*Finds shortest paths between nodes and updates in distance map, if shorter than existing paths*/
        private void findShortestPath(City d){
            List<City> neighbors = findNeighbors(d);

            for(City city : neighbors){
                if(shortestDistance(city) > (shortestDistance(d) + findDistance(d,city))){
                    distance.put(city,shortestDistance(d) + findDistance(d, city));
                    predecessors.put(city, d);
                    unknown.add(city);
                }
            }
        }


    private int shortestDistance(City d){
        Integer i = distance.get(d);
        if(i == null){
            return Integer.MAX_VALUE;
        }
        else{
            return i;
        }
    }
            /*Returns the distance in minutes between two destinations*/
        private int findDistance(City start, City end){
            for(road r : roads){
                if(r.start.equals(start) && r.end.equals(end)){
                    return r.minutes;
                }
                if(r.start.equals(end) && r.end.equals(start)){
                    return r.minutes;
                }
            }
            return Integer.MAX_VALUE;
        }

            /*Returns the distance in miles between two destinations*/
        private int findMiles(City start, City end){
            for(road r : roads){
                if(r.start.equals(start) && r.end.equals(end)){
                    return r.miles;
                }
                if(r.start.equals(end) && r.end.equals(start)){
                    return r.miles;
                }
            }
            return Integer.MAX_VALUE;
        }

            /*Finds and returns a list of neighbors for the given node*/
        private List<City> findNeighbors(City d){

        List<City> neighbors = new ArrayList<City>();
        for(road r : roads){
            if(!known.contains(r.start) && r.end.equals(d)){
                neighbors.add(r.start);
            }
            if(!known.contains(r.end) && r.start.equals(d)){
                neighbors.add(r.end);
            }
        }
        return neighbors;
        }

    }





