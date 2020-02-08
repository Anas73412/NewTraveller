package in.binplus.travel.Model;

public class AvailableBusesModel {

    String id,company_name,owner_name,registration_no,chechis_number,veh_model,vehicle_type,sitting_type,vehicle_name,veh_description,status,v_type,vehicles_type,vehicles_path,vehicles_end,from_time,to_time;
    String total_seats,seat_fare;
    public AvailableBusesModel() {
    }

    public AvailableBusesModel(String id, String owner_name,String company_name, String registration_no, String chechis_number, String veh_model, String vehicle_type, String sitting_type, String vehicle_name, String veh_description, String status, String v_type, String vehicles_type, String vehicles_path,String vehicles_end, String from_time, String to_time,String total_seats, String seat_fare) {
        this.id = id;
        this.owner_name = owner_name;
        this.company_name = company_name;
        this.registration_no = registration_no;
        this.chechis_number = chechis_number;
        this.veh_model = veh_model;
        this.vehicle_type = vehicle_type;
        this.sitting_type = sitting_type;
        this.vehicle_name = vehicle_name;
        this.veh_description = veh_description;
        this.status = status;
        this.v_type = v_type;
        this.vehicles_type = vehicles_type;
        this.vehicles_path = vehicles_path;
        this.vehicles_end = vehicles_end;
        this.from_time = from_time;
        this.to_time = to_time;
        this.total_seats = total_seats;
        this.seat_fare = seat_fare;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwner_name() {
        return owner_name;
    }

    public void setOwner_name(String owner_name) {
        this.owner_name = owner_name;
    }

    public String getRegistration_no() {
        return registration_no;
    }

    public void setRegistration_no(String registration_no) {
        this.registration_no = registration_no;
    }

    public String getChechis_number() {
        return chechis_number;
    }

    public void setChechis_number(String chechis_number) {
        this.chechis_number = chechis_number;
    }

    public String getVeh_model() {
        return veh_model;
    }

    public void setVeh_model(String veh_model) {
        this.veh_model = veh_model;
    }

    public String getVehicle_type() {
        return vehicle_type;
    }

    public void setVehicle_type(String vehicle_type) {
        this.vehicle_type = vehicle_type;
    }

    public String getSitting_type() {
        return sitting_type;
    }

    public void setSitting_type(String sitting_type) {
        this.sitting_type = sitting_type;
    }

    public String getVehicle_name() {
        return vehicle_name;
    }

    public void setVehicle_name(String vehicle_name) {
        this.vehicle_name = vehicle_name;
    }

    public String getVeh_description() {
        return veh_description;
    }

    public void setVeh_description(String veh_description) {
        this.veh_description = veh_description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getV_type() {
        return v_type;
    }

    public void setV_type(String v_type) {
        this.v_type = v_type;
    }

    public String getVehicles_type() {
        return vehicles_type;
    }

    public void setVehicles_type(String vehicles_type) {
        this.vehicles_type = vehicles_type;
    }

    public String getVehicles_path() {
        return vehicles_path;
    }

    public void setVehicles_path(String vehicles_path) {
        this.vehicles_path = vehicles_path;
    }

    public String getFrom_time() {
        return from_time;
    }

    public void setFrom_time(String from_time) {
        this.from_time = from_time;
    }

    public String getTo_time() {
        return to_time;
    }

    public void setTo_time(String to_time) {
        this.to_time = to_time;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getVehicles_end() {
        return vehicles_end;
    }

    public void setVehicles_end(String vehicles_end) {
        this.vehicles_end = vehicles_end;
    }

    public String getTotal_seats() {
        return total_seats;
    }

    public void setTotal_seats(String total_seats) {
        this.total_seats = total_seats;
    }

    public String getSeat_fare() {
        return seat_fare;
    }

    public void setSeat_fare(String seat_fare) {
        this.seat_fare = seat_fare;
    }
}