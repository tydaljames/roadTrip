import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class DataHolder {

    private final ArrayList<Road> roads;
    private final ArrayList<City> cities;
    private final ArrayList<String> attractions;

    public DataHolder() {
        this.roads = new ArrayList<>();
        this.cities = new ArrayList<>();
        this.attractions = new ArrayList<>();
    }

    public DataHolder(String[] args) throws IOException {
        this.roads = new ArrayList<>();
        this.cities = new ArrayList<>();
        this.attractions = new ArrayList<>();
        parseAndGetData(args);
    }

    public ArrayList<Road> getRoads() {
        return roads;
    }

    public ArrayList<City> getCities() {
        return cities;
    }

    public ArrayList<String> getAttractions() {
        return attractions;
    }

    /*Prints a list of all possible cities and attractions*/
    public void cityList(){
        System.out.println("List of cities: \n");

        for(int i = 0; i< cities.size(); i++){
            City city = cities.get(i);

            for(int j = 0; j<city.getAttractionsList().size(); j++){
                System.out.println((i+1) + " " + city.getCityName() + " -- " + (j+1) + " " + city.getAttractionsList().get(j));
            }

            if(city.getAttractionsList().size() == 0){
                System.out.println((i+1) + " " + city.getCityName());
            }
        }
    }

    /*Prints a list of all possible roads*/
    public void roadList(){
        for(int i = 0; i < roads.size(); i++){
            System.out.println(i + " " + roads.get(i).start.getCityName() + ", " + roads.get(i).end.getCityName());
        }
    }

    private void parseAndGetData(String[] args) throws IOException {
        parseArgs(args);
        getData(Path.of(args[0]), Path.of(args[1]));
    }

    private void parseArgs(String[] args){
        if(args.length != 2){
            System.out.println("Must have exactly two command line arguments, one for each csv input file. Exiting.");
            System.exit(0);
        }
        for(String arg : args){
            if(!arg.endsWith(".csv") && Files.isRegularFile(Path.of(arg))){
                System.out.println("Command line arguments must be for two valid CSV files. Exiting.");
                System.exit(0);
            }
        }
    }

    /*Gathers all data from the input .csv files and creates associated lists and objects*/
    private void getData(Path inputOne, Path inputTwo) throws IOException {
        String line;

        try (BufferedReader br = Files.newBufferedReader(inputOne, StandardCharsets.ISO_8859_1)) {

            while((line = br.readLine()) != null){
                City source = null;
                City target = null;

                String[] split = line.split(",");
                if(!cities.stream().anyMatch(o -> o.getCityName().equalsIgnoreCase(split[0]))) {
                    cities.add(source = new City(split[0]));
                }
                if(!cities.stream().anyMatch(o -> o.getCityName().equalsIgnoreCase(split[1]))){
                    cities.add(target = new City(split[1]));
                }

                for (City city : cities){
                    if(city.getCityName().equalsIgnoreCase(split[0])){
                        source = city;
                    }
                    if(city.getCityName().equalsIgnoreCase(split[1])){
                        target = city;
                    }
                    if(source != null && target != null){
                        Road newroad = new Road(source, target, Integer.parseInt(split[2]), Integer.parseInt(split[3]));
                        roads.add(newroad);
                        break;
                    }
                }
            }
        }

        try (BufferedReader br = Files.newBufferedReader(inputTwo, StandardCharsets.ISO_8859_1)) {
            while((line = br.readLine()) != null) {
                String[] split = line.split(",");

                for(City city : cities){
                    if(city.getCityName().equalsIgnoreCase(split[1])){
                        city.addAttraction(split[0]);
                    }
                }
            }
        }
    }
}
