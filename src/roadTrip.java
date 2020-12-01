import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class roadTrip {
    String start_city = "";
    String end_city = "";
    ArrayList<road> roads;
    ArrayList<destination> destinations;
    ArrayList<String> attractions;



        /*Checks for valid user inputs for start_city and end_city*/
    private boolean checkInput(String input){
        for(int i=0;i<destinations.size();i++){
            if(destinations.get(i).city.equalsIgnoreCase(input)){
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
        for(int j=0;j<destinations.size();j++){
            destination d = destinations.get(j);
            if(d.attraction.size()>0){
                for(int k=0;k<d.attraction.size();k++){
                    if(d.attraction.get(k).equalsIgnoreCase(input)){
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

        for(int i=0;i<destinations.size();i++){
            destination d = destinations.get(i);
            for(int j=0;j<d.attraction.size();j++){
                System.out.println((i+1) + " " + d.city + " -- " + (j+1) + " " + d.attraction.get(j));
            }
            if(d.attraction.size() == 0){
                System.out.println((i+1) + " " + d.city);
            }

        }
    }

        /*Prints a list of all possible roads*/
    public void roadList(){

        int i=0;
        for(road r : roads){
            i++;
            System.out.println(i + " " + r.start.city + ", " + r.end.city);
        }
    }

        /*Gathers all data from the input .csv files and creates associated lists and objects*/
    private void getData() throws IOException {
        roads = new ArrayList<>();
        destinations = new ArrayList<>();
        attractions = new ArrayList<>();

        destination source = null;
        destination target = null;
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

                for(int i=0;i<destinations.size();i++){
                    destination d = destinations.get(i);
                    if(d.city.equalsIgnoreCase(data[0])){
                        city_exists = true;
                    }
                }
                if(city_exists == false){
                    destination d = new destination(data[0]);
                    destinations.add(d);
                }
                city_exists = false;

                for(int i=0;i<destinations.size();i++){
                    destination d = destinations.get(i);
                    if(d.city.equalsIgnoreCase(data[1])){
                        city_exists = true;
                    }
                }
                if(city_exists == false){
                    destination d = new destination(data[1]);
                    destinations.add(d);
                }
                city_exists = false;

                for (destination d : destinations){
                    if(d.city.equalsIgnoreCase(data[0])){
                        source = d;
                    }
                    if(d.city.equalsIgnoreCase(data[1])){
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


                for(int i=0;i<destinations.size();i++){
                    destination d = destinations.get(i);
                    if(d.city.equalsIgnoreCase(data[1])){

                        d.attraction.add(data[0]);
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
                start_city = sc.nextLine();
                if(start_city.equalsIgnoreCase("list")){
                    destinationList();
                }
            }
            while(checkInput(start_city) == false);

            do {
                System.out.println("Enter the city you would like to end:");
                end_city = sc.nextLine();
                if(end_city.equalsIgnoreCase("list")){
                    destinationList();
                }
            }
            while(checkInput(end_city) == false);


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
        pathFinder p = new pathFinder(destinations,roads);
        List<path> paths = (p.route(start_city, end_city, attractions));

        for(path path : paths){
            if(path == null){
                System.out.println("");
            }
            else {
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
