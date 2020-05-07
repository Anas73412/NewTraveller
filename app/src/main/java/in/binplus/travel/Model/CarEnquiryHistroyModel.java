package in.binplus.travel.Model;

import java.util.ArrayList;

public class CarEnquiryHistroyModel {
    String enquiry_id ;
    String f_id ;
    String vehicle_id ;
    String from_location;
    String to_locations ;
    String journey_date ;
    String enquiry_date ;
    String name ;
    String mobile_no ;
    String adhaar_no ;
    String note ;
    String stop_list;
    String status ;


    public CarEnquiryHistroyModel() {
    }

    public CarEnquiryHistroyModel(String enquiry_id, String f_id, String vehicle_id, String from_location, String to_locations, String journey_date, String enquiry_date, String name, String mobile_no, String adhaar_no, String note, String stop_list, String status) {
        this.enquiry_id = enquiry_id;
        this.f_id = f_id;
        this.vehicle_id = vehicle_id;
        this.from_location = from_location;
        this.to_locations = to_locations;
        this.journey_date = journey_date;
        this.enquiry_date = enquiry_date;
        this.name = name;
        this.mobile_no = mobile_no;
        this.adhaar_no = adhaar_no;
        this.note = note;
        this.stop_list = stop_list;
        this.status = status;
    }

    public String getEnquiry_id() {
        return enquiry_id;
    }

    public void setEnquiry_id(String enquiry_id) {
        this.enquiry_id = enquiry_id;
    }

    public String getF_id() {
        return f_id;
    }

    public void setF_id(String f_id) {
        this.f_id = f_id;
    }

    public String getVehicle_id() {
        return vehicle_id;
    }

    public void setVehicle_id(String vehicle_id) {
        this.vehicle_id = vehicle_id;
    }

    public String getFrom_location() {
        return from_location;
    }

    public void setFrom_location(String from_location) {
        this.from_location = from_location;
    }

    public String getTo_locations() {
        return to_locations;
    }

    public void setTo_locations(String to_locations) {
        this.to_locations = to_locations;
    }

    public String getJourney_date() {
        return journey_date;
    }

    public void setJourney_date(String journey_date) {
        this.journey_date = journey_date;
    }

    public String getEnquiry_date() {
        return enquiry_date;
    }

    public void setEnquiry_date(String enquiry_date) {
        this.enquiry_date = enquiry_date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getStop_list() {
        return stop_list;
    }

    public void setStop_list(String stop_list) {
        this.stop_list = stop_list;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
