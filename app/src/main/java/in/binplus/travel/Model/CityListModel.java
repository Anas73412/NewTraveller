package in.binplus.travel.Model;

public class CityListModel {
    String title ;
    String city_code;
    String description;

    public CityListModel(String title, String city_code, String description) {
        this.title = title;
        this.city_code = city_code;
        this.description = description;
    }

    public CityListModel() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCity_code() {
        return city_code;
    }

    public void setCity_code(String city_code) {
        this.city_code = city_code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
