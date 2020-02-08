package in.binplus.travel.Model;

public class PassengerDetailsModel {
    String id;
    String seat_no;
    String seat_price;
    String name;
    String age;
    String gender;
    String nationality;

    public PassengerDetailsModel() {
    }

    public PassengerDetailsModel(String id, String seat_no, String seat_price, String name, String age, String gender, String nationality) {
        this.id = id;
        this.seat_no = seat_no;
        this.seat_price = seat_price;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.nationality = nationality;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSeat_no() {
        return seat_no;
    }

    public void setSeat_no(String seat_no) {
        this.seat_no = seat_no;
    }

    public String getSeat_price() {
        return seat_price;
    }

    public void setSeat_price(String seat_price) {
        this.seat_price = seat_price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }
}