import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Stores City name and a list of attractions for that City.
 */
public class City {

    /**
     * Name of the City.
     */
    private String cityName;

    /**
     * A list of attractions in the City.
     */
    private final ArrayList<String> attractionsList;

    /**
     * Constructor for City object.
     *
     * @param cityName the name of the City to be instantiated.
     */
    public City(String cityName){
        this.cityName = cityName;
        this.attractionsList = new ArrayList<>();
    }

    /**
     * Returns the name of the City object.
     *
     * @return the name of the City object.
     */
    public String getCityName() {
        return cityName;
    }

    /**
     * Sets the name of the City object.
     */
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    /**
     * Returns the City's list of attractions.
     *
     * @return the City's list of attractions.
     */
    public List<String> getAttractionsList() {
        return Collections.unmodifiableList(attractionsList);
    }

    /**
     * Adds a new attraction to the attractionsList.
     *
     * @param attraction the attraction to be added.
     */
    public void addAttraction(String attraction) {
        if(!attractionsList.contains(attraction)){
            attractionsList.add(attraction);
        }
    }

    /**
     * Returns the name of the City object.
     *
     * @return the name of the City object.
     */
    @Override
    public String toString() {
        return cityName;
    }
}
