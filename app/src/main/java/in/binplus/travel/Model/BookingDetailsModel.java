package in.binplus.travel.Model;


public class BookingDetailsModel {
    String id ;
    String booking_id;
    String vehicle_id;
    String status ;
    String user_id;
    String payment_type ;
    String total_money ;

    String vehicle_type;
    String vehicle_no ;
    String start_from ;
    String end_to ;
    String booking_date ;
    String journey_startdate ;

    String vehicle_category;
    String vehicle_name ;
    String note ;
    String f_id ;

    String b_name ;
    String address ;
    String adhar_no ;
    String mobile;
    String email ;
  String tot_seats ;
  String seat_no ;
  String drop_location ;
  String board_location ;
  String cancelled_by ;
  String cancelled_type ;

    public BookingDetailsModel() {
    }

    public BookingDetailsModel(String id, String booking_id, String vehicle_id, String status, String user_id, String payment_type, String total_money, String vehicle_type, String vehicle_no, String start_from, String end_to, String booking_date, String journey_startdate, String vehicle_category, String vehicle_name, String note, String f_id, String b_name, String address, String adhar_no, String mobile, String email, String tot_seats, String seat_no, String drop_location, String board_location, String cancelled_by, String cancelled_type) {
        this.id = id;
        this.booking_id = booking_id;
        this.vehicle_id = vehicle_id;
        this.status = status;
        this.user_id = user_id;
        this.payment_type = payment_type;
        this.total_money = total_money;
        this.vehicle_type = vehicle_type;
        this.vehicle_no = vehicle_no;
        this.start_from = start_from;
        this.end_to = end_to;
        this.booking_date = booking_date;
        this.journey_startdate = journey_startdate;
        this.vehicle_category = vehicle_category;
        this.vehicle_name = vehicle_name;
        this.note = note;
        this.f_id = f_id;
        this.b_name = b_name;
        this.address = address;
        this.adhar_no = adhar_no;
        this.mobile = mobile;
        this.email = email;
        this.tot_seats = tot_seats;
        this.seat_no = seat_no;
        this.drop_location = drop_location;
        this.board_location = board_location;
        this.cancelled_by = cancelled_by;
        this.cancelled_type = cancelled_type;
    }

    public String getCancelled_by() {
        return cancelled_by;
    }

    public void setCancelled_by(String cancelled_by) {
        this.cancelled_by = cancelled_by;
    }

    public String getCancelled_type() {
        return cancelled_type;
    }

    public void setCancelled_type(String cancelled_type) {
        this.cancelled_type = cancelled_type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(String booking_id) {
        this.booking_id = booking_id;
    }

    public String getVehicle_id() {
        return vehicle_id;
    }

    public void setVehicle_id(String vehicle_id) {
        this.vehicle_id = vehicle_id;
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

    public String getVehicle_no() {
        return vehicle_no;
    }

    public void setVehicle_no(String vehicle_no) {
        this.vehicle_no = vehicle_no;
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

    public String getVehicle_category() {
        return vehicle_category;
    }

    public void setVehicle_category(String vehicle_category) {
        this.vehicle_category = vehicle_category;
    }

    public String getVehicle_name() {
        return vehicle_name;
    }

    public void setVehicle_name(String vehicle_name) {
        this.vehicle_name = vehicle_name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getF_id() {
        return f_id;
    }

    public void setF_id(String f_id) {
        this.f_id = f_id;
    }

    public String getB_name() {
        return b_name;
    }

    public void setB_name(String b_name) {
        this.b_name = b_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAdhar_no() {
        return adhar_no;
    }

    public void setAdhar_no(String adhar_no) {
        this.adhar_no = adhar_no;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTot_seats() {
        return tot_seats;
    }

    public void setTot_seats(String tot_seats) {
        this.tot_seats = tot_seats;
    }

    public String getSeat_no() {
        return seat_no;
    }

    public void setSeat_no(String seat_no) {
        this.seat_no = seat_no;
    }

    public String getDrop_location() {
        return drop_location;
    }

    public void setDrop_location(String drop_location) {
        this.drop_location = drop_location;
    }

    public String getBoard_location() {
        return board_location;
    }

    public void setBoard_location(String board_location) {
        this.board_location = board_location;
    }
}
