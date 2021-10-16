import java.util.ArrayList;
import java.util.Scanner;

public class UserInterface {


    /**
     * The user-entered city that the Road Trip will start at.
     */
    private String startCity;

    /**
     * The user-entered city that the Road Trip will end at.
     */
    private String endCity;

    private final DataHolder dh;

    public UserInterface(DataHolder dh) {
        this.dh = dh;
    }

    public String getStartCity() {
        return startCity;
    }

    public String getEndCity() {
        return endCity;
    }

    /*Checks for valid user inputs for startCity and endCity*/
    private boolean checkInput(String input){
        for(City city : dh.getCities()){
            if(city.getCityName().equalsIgnoreCase(input)){
                return true;
            }
        }
        return false;
    }

    /*Checks for valid user inputs for attractions, and adds to list of attractions if valid*/
    private boolean checkAttraction(String input){
        for(City city : dh.getCities()){
            if(city.getAttractionsList().size()>0){

                for(String attraction : city.getAttractionsList()){
                    if(attraction.equalsIgnoreCase(input) && !dh.getAttractions().contains(input)){
                        dh.getAttractions().add(input);
                        return true;
                    }
                }
            }
        }
        return false;
    }





    /*Gathers all users inputs */
    public void userInput(){
        Scanner scanner = new Scanner(System.in);
        String stop = "";
        int stops = -1;

        try{
            System.out.println("\nWelcome to roadTrip. This program accepts inputs in the following format:");
            System.out.println("San Francisco CA, Denver CO, Grand Canyon, etc.");

            do{
                System.out.println("Enter the city you would like to start, or enter list to see the full list of options:");
                startCity = scanner.nextLine();
                if(startCity.equalsIgnoreCase("list")){
                    dh.cityList();
                }
            }
            while(!checkInput(startCity));

            do {
                System.out.println("Enter the city you would like to end:");
                endCity = scanner.nextLine();
                if(endCity.equalsIgnoreCase("list")){
                    dh.cityList();
                }
            }
            while(!checkInput(endCity));

            System.out.println("How many stops would you like to make on the way?");

            do {
                try {
                    stops = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Enter a number of attractions you would like to visit.");
                }
            }
            while(stops < 0);

            for(int i=0;i<stops;i++) {
                do{
                    System.out.println("Enter the #" + (i+1) + " attraction that you would like to visit:");
                    stop = scanner.nextLine();
                    if(stop.equalsIgnoreCase("list")){
                        dh.cityList();
                    }
                }
                while(!checkAttraction(stop));
            }
        }
        catch(Exception e){
            System.out.println("Error occurred while handling user input.");
        }
    }
}
