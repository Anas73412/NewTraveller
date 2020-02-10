package in.binplus.travel.Model;

public class BoardingPointModel {
    String location_name ;
    String location_desc ;
    String location_time ;

    public BoardingPointModel(String location_name, String location_desc, String location_time)
    {
        this.location_name = location_name;
        this.location_desc = location_desc;
        this.location_time = location_time;
    }

    public BoardingPointModel() {
    }

    public String getLocation_name() {
        return location_name;
    }

    public void setLocation_name(String location_name) {
        this.location_name = location_name;
    }

    public String getLocation_desc() {
        return location_desc;
    }

    public void setLocation_desc(String location_desc) {
        this.location_desc = location_desc;
    }

    public String getLocation_time() {
        return location_time;
    }

    public void setLocation_time(String location_time) {
        this.location_time = location_time;
    }
}
