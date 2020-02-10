package in.binplus.travel.Model;

public class StopsModel {
   String stop_name ;
   String stop_desc ;

    public StopsModel(String stop_name, String stop_desc) {
        this.stop_name = stop_name;
        this.stop_desc = stop_desc;
    }

    public StopsModel() {
    }

    public String getStop_name() {
        return stop_name;
    }

    public void setStop_name(String stop_name) {
        this.stop_name = stop_name;
    }

    public String getStop_desc() {
        return stop_desc;
    }

    public void setStop_desc(String stop_desc) {
        this.stop_desc = stop_desc;
    }
}
