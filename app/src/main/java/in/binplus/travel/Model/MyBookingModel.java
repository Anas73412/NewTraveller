package in.binplus.travel.Model;

public class MyBookingModel {
    String booking_id ;
    String vehicles_id ;
    String status ;
    String user_id ;
    String payment_type;
    String total_money ;
    String vehicle_type ;
    String start_from ;
    String end_to ;
    String booking_date ;
    String journey_startdate ;
    String journey_enddate;
    String passenger_id;
    String vehicle_categories;
    String vehicle_name ;
    String vehicle_no ;

    public MyBookingModel(String booking_id, String vehicles_id, String status, String user_id, String payment_type, String total_money, String vehicle_type, String start_from, String end_to, String booking_date, String journey_startdate, String journey_enddate, String passenger_id, String vehicle_categories, String vehicle_name, String vehicle_no) {
        this.booking_id = booking_id;
        this.vehicles_id = vehicles_id;
        this.status = status;
        this.user_id = user_id;
        this.payment_type = payment_type;
        this.total_money = total_money;
        this.vehicle_type = vehicle_type;
        this.start_from = start_from;
        this.end_to = end_to;
        this.booking_date = booking_date;
        this.journey_startdate = journey_startdate;
        this.journey_enddate = journey_enddate;
        this.passenger_id = passenger_id;
        this.vehicle_categories = vehicle_categories;
        this.vehicle_name = vehicle_name;
        this.vehicle_no = vehicle_no;
    }

    public String getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(String booking_id) {
        this.booking_id = booking_id;
    }

    public String getVehicles_id() {
        return vehicles_id;
    }

    public void setVehicles_id(String vehicles_id) {
        this.vehicles_id = vehicles_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

    public String getTotal_money() {
        return total_money;
    }

    public void setTotal_money(String total_money) {
        this.total_money = total_money;
    }

    public String getVehicle_type() {
        return vehicle_type;
    }

    public void setVehicle_type(String vehicle_type) {
        this.vehicle_type = vehicle_type;
    }

    public String getStart_from() {
        return start_from;
    }

    public void setStart_from(String start_from) {
        this.start_from = start_from;
    }

    public String getEnd_to() {
        return end_to;
    }

    public void setEnd_to(String end_to) {
        this.end_to = end_to;
    }

    public String getBooking_date() {
        return booking_date;
    }

    public void setBooking_date(String booking_date) {
        this.booking_date = booking_date;
    }

    public String getJourney_startdate() {
        return journey_startdate;
    }

    public void setJourney_startdate(String journey_startdate) {
        this.journey_startdate = journey_startdate;
    }

    public String getJourney_enddate() {
        return journey_enddate;
    }

    public void setJourney_enddate(String journey_enddate) {
        this.journey_enddate = journey_enddate;
    }

    public String getPassenger_id() {
        return passenger_id;
    }

    public void setPassenger_id(String passenger_id) {
        this.passenger_id = passenger_id;
    }

    public String getVehicle_categories() {
        return vehicle_categories;
    }

    public void setVehicle_categories(String vehicle_categories) {
        this.vehicle_categories = vehicle_categories;
    }

    public String getVehicle_name() {
        return vehicle_name;
    }

    public void setVehicle_name(String vehicle_name) {
        this.vehicle_name = vehicle_name;
    }

    public String getVehicle_no() {
        return vehicle_no;
    }

    public void setVehicle_no(String vehicle_no) {
        this.vehicle_no = vehicle_no;
    }
}
