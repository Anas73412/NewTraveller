package in.binplus.travel.Model;

/**
 * Developed by Binplus Technologies pvt. ltd.  on 10,February,2020
 */
public class BoardingModel {

    String location,time;

    public BoardingModel() {
    }

    public BoardingModel(String location, String time) {
        this.location = location;
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
