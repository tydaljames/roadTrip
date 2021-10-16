import java.util.*;

public class ShortestPathFinder {
    private List<City> Cities;
    private List<Road> roads;
    private Map<City, City> predecessors;
    private Map<City, Integer> distance;
    private Set<City> known;
    private Set<City> unknown;

    public ShortestPathFinder(ArrayList<City> Cities, ArrayList<Road> roads) {
        this.Cities = Cities;
        this.roads = roads;
    }

        /*Sets distances to destinations using Dijkstra's algorithm*/
    public void dijkstras(City startCity) {
        predecessors = new HashMap<>();
        distance = new HashMap<>();
        known = new HashSet<>();
        unknown = new HashSet<>();

        distance.put(startCity, 0);
        unknown.add(startCity);
        while (unknown.size() > 0) {
            City d = findLowest(unknown);
            known.add(d);
            unknown.remove(d);
            findShortestPath(d);
        }
    }


        /*Uses distances set by Dijkstra's Algorithm to find the shortest path to endCity*/
    public TripSegment findPath(City startCity, City endCity) {
        int totalMinutes = 0;
        int totalMiles = 0;
        List<City> route = new ArrayList<>();

        City cur = endCity;
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
            totalMinutes += findDistance(route.get(i - 1), route.get(i));
            totalMiles += findMiles(route.get(i - 1), route.get(i));
        }

        /*Path object is created containing all data on the current startCity to endCity path segment*/
        TripSegment newpath = new TripSegment(route, totalMinutes, totalMiles, startCity, endCity);
        return newpath;
    }

    /*Runs all subfunctions and delivers the returns the resulting list of paths*/
    public List<TripSegment> route(String startingCity, String endingCity, List<String> attractions) {
        City start = null;
        City end = null;
        List<City> attractionList = new ArrayList<>();
        List<TripSegment> tripSegments = new ArrayList<>();


            /*Converts strings to destination objects for later use*/
        for (City d : Cities) {
            if (d.getCityName().equalsIgnoreCase(startingCity)) {
                start = d;
            }
            if (d.getCityName().equalsIgnoreCase(endingCity)) {
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
            tripSegments.add(findPath(start, nearest));
            start = nearest;
            dijkstras(start);
        }

        tripSegments.add(findPath(nearest,end));
        return tripSegments;
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
            for(Road r : roads){
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
            for(Road r : roads){
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
        for(Road r : roads){
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





