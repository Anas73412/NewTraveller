package in.binplus.travel.Model;

public class AddPassengerToSeatModel {
//    String seat_type ;
//    String seat_id ;

//    String seat_status ;
//    String bus_type ;

    String passenger_name ;
    String age ;
    String gender ;
    String nationality ;
    String seat_no ;
    String mobile_no;
    String adhaar_no;
    String seat_price ;


    public AddPassengerToSeatModel() {
    }

    public AddPassengerToSeatModel(String passenger_name, String age, String gender, String nationality, String seat_no, String mobile_no, String adhaar_no, String seat_price) {
        this.passenger_name = passenger_name;
        this.age = age;
        this.gender = gender;
        this.nationality = nationality;
        this.seat_no = seat_no;
        this.mobile_no = mobile_no;
        this.adhaar_no = adhaar_no;
        this.seat_price = seat_price;
    }

    public String getPassenger_name() {
        return passenger_name;
    }

    public void setPassenger_name(String passenger_name) {
        this.passenger_name = passenger_name;
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

    public String getSeat_no() {
        return seat_no;
    }

    public void setSeat_no(String seat_no) {
        this.seat_no = seat_no;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getAdhaar_no() {
        return adhaar_no;
    }

    public void setAdhaar_no(String adhaar_no) {
        this.adhaar_no = adhaar_no;
    }

    public String getSeat_price() {
        return seat_price;
    }

    public void setSeat_price(String seat_price) {
        this.seat_price = seat_price;
    }
}
