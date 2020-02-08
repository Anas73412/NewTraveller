package in.binplus.travel.Model;

public class SeatModel {
    String seat_no ;
    String seat_type ;
    String seat_id ;
    String seat_price ;
    String seat_status ;
    String bus_type ;

    public SeatModel(String seat_no, String seat_type, String seat_id, String seat_price, String seat_status, String bus_type) {
        this.seat_no = seat_no;
        this.seat_type = seat_type;
        this.seat_id = seat_id;
        this.seat_price = seat_price;
        this.seat_status = seat_status;
        this.bus_type = bus_type;
    }

    public String getSeat_no() {
        return seat_no;
    }

    public void setSeat_no(String seat_no) {
        this.seat_no = seat_no;
    }

    public String getSeat_type() {
        return seat_type;
    }

    public void setSeat_type(String seat_type) {
        this.seat_type = seat_type;
    }

    public String getSeat_id() {
        return seat_id;
    }

    public void setSeat_id(String seat_id) {
        this.seat_id = seat_id;
    }

    public String getSeat_price() {
        return seat_price;
    }

    public void setSeat_price(String seat_price) {
        this.seat_price = seat_price;
    }

    public String getSeat_status() {
        return seat_status;
    }

    public void setSeat_status(String seat_status) {
        this.seat_status = seat_status;
    }

    public String getBus_type() {
        return bus_type;
    }

    public void setBus_type(String bus_type) {
        this.bus_type = bus_type;
    }
}
