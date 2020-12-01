import java.util.ArrayList;

public class destination {

    public String city;
    public ArrayList<String> attraction;

    @Override
    public String toString() {
        return city;
    }

    public destination(String city){
        this.city = city;
        this.attraction = new ArrayList<>();
    }

}
