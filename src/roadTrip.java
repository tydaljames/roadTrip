import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class roadTrip {

    /**
     * The user-entered city that the Road Trip will start at.
     */
    private String startCity;

    /**
     * The user-entered city that the Road Trip will end at.
     */
    private String endCity;

    /**
     * A list containing all of the roads.
     */
    private final ArrayList<road> roads;
    private final ArrayList<City> Cities;
    private final ArrayList<String> attractions;

    public roadTrip() {
        this.roads = new ArrayList<>();
        this.Cities = new ArrayList<>();
        this.attractions = new ArrayList<>();
    }

    /*Checks for valid user inputs for startCity and endCity*/
    private boolean checkInput(String input){
        for(int i = 0; i< Cities.size(); i++){
            if(Cities.get(i).getCityName().equalsIgnoreCase(input)){
                return true;
            }
        }
        return false;
        }

    /*Checks for valid user inputs for attractions, and adds to list of attractions if valid*/
    private boolean checkAttraction(String input){
        for(int i=0;i<attractions.size();i++){
            if(attractions.get(i).equalsIgnoreCase(input)){
                return false;
            }
        }
        for(int j = 0; j< Cities.size(); j++){
            City d = Cities.get(j);
            if(d.getAttractionsList().size()>0){
                for(int k = 0; k<d.getAttractionsList().size(); k++){
                    if(d.getAttractionsList().get(k).equalsIgnoreCase(input)){
                        attractions.add(input);
                        return true;
                }
            }
            }
        }
        return false;
    }


        /*Prints a list of all possible destinations and attractions*/
    public void destinationList(){
        System.out.println("List of destinations: \n");

        for(int i = 0; i< Cities.size(); i++){
            City d = Cities.get(i);
            for(int j = 0; j<d.getAttractionsList().size(); j++){
                System.out.println((i+1) + " " + d.getCityName() + " -- " + (j+1) + " " + d.getAttractionsList().get(j));
            }
            if(d.getAttractionsList().size() == 0){
                System.out.println((i+1) + " " + d.getCityName());
            }

        }
    }

        /*Prints a list of all possible roads*/
    public void roadList(){

        int i=0;
        for(road r : roads){
            i++;
            System.out.println(i + " " + r.start.getCityName() + ", " + r.end.getCityName());
        }
    }

        /*Gathers all data from the input .csv files and creates associated lists and objects*/
    private void getData() throws IOException {

        City source = null;
        City target = null;
        boolean city_exists = false;
        BufferedReader bf = null;

        try {
            bf = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/roads.csv"));
        }

        catch (FileNotFoundException e){
            System.out.println("Move roads.csv to the proper directory.");
        }
            String line;
            while((line = bf.readLine()) != null){
                String[] data = line.split(",");

                for(int i = 0; i< Cities.size(); i++){
                    City d = Cities.get(i);
                    if(d.getCityName().equalsIgnoreCase(data[0])){
                        city_exists = true;
                    }
                }
                if(city_exists == false){
                    City d = new City(data[0]);
                    Cities.add(d);
                }
                city_exists = false;

                for(int i = 0; i< Cities.size(); i++){
                    City d = Cities.get(i);
                    if(d.getCityName().equalsIgnoreCase(data[1])){
                        city_exists = true;
                    }
                }
                if(city_exists == false){
                    City d = new City(data[1]);
                    Cities.add(d);
                }
                city_exists = false;

                for (City d : Cities){
                    if(d.getCityName().equalsIgnoreCase(data[0])){
                        source = d;
                    }
                    if(d.getCityName().equalsIgnoreCase(data[1])){
                        target = d;
                    }

                }
                if(source != null && target != null) {
                    road newroad = new road(source, target, Integer.parseInt(data[2]), Integer.parseInt(data[3]));
                    roads.add(newroad);
                }
            }

            bf.close();


        try {
            bf = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/attractions.csv"));
        }

        catch (FileNotFoundException e){
            System.out.println("Move attractions.csv to the proper directory.");
        }

        while((line = bf.readLine()) != null) {
            String[] data = line.split(",");


                for(int i = 0; i< Cities.size(); i++){
                    City d = Cities.get(i);
                    if(d.getCityName().equalsIgnoreCase(data[1])){

                        d.addAttraction(data[0]);
                    }
                }
        }

        bf.close();
    }


        /*Gathers all users inputs */
    private void userInput(){
        Scanner sc = new Scanner(System.in);
        String stop = "";
        int stops = -1;

        try{
            System.out.println("\nWelcome to roadTrip. This program accepts inputs in the following format:");
            System.out.println("San Francisco CA, Denver CO, Grand Canyon, etc.");

            do{
                System.out.println("Enter the city you would like to start, or enter list to see the full list of options:");
                startCity = sc.nextLine();
                if(startCity.equalsIgnoreCase("list")){
                    destinationList();
                }
            }
            while(checkInput(startCity) == false);

            do {
                System.out.println("Enter the city you would like to end:");
                endCity = sc.nextLine();
                if(endCity.equalsIgnoreCase("list")){
                    destinationList();
                }
            }
            while(checkInput(endCity) == false);


            System.out.println("How many stops would you like to make on the way?");

            do {
                try {
                    stops = Integer.parseInt(sc.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Enter a number of attractions you would like to visit.");
                }
            }
            while(stops < 0);


            for(int i=0;i<stops;i++) {
                do{
                    System.out.println("Enter the #" + (i+1) + " attraction that you would like to visit:");
                    stop = sc.nextLine();
                    if(stop.equalsIgnoreCase("list")){
                        destinationList();
                    }
                }
                while(checkAttraction(stop) == false);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

        /*Runs pathfinder, uses Dijkstra's Algorithm to find shortest paths.*/
    private void runAlgo(){
        pathFinder p = new pathFinder(Cities,roads);
        List<Path> Paths = (p.route(startCity, endCity, attractions));

        for(Path path : Paths){
            if(path != null){
                System.out.println(path);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        roadTrip r = new roadTrip();
        r.getData();
        r.userInput();
        r.runAlgo();
    }
}
