package in.binplus.travel.Model;

public class REchargeHistoryModel {
    String id ;
    String current_amount ;
    String request_amount ;
    String date ;
    String status ;
    String user_id ;
    String time ;
    String desc ;

    public REchargeHistoryModel() {
    }

    public REchargeHistoryModel(String id, String current_amount, String request_amount, String date, String status, String user_id, String time, String desc) {
        this.id = id;
        this.current_amount = current_amount;
        this.request_amount = request_amount;
        this.date = date;
        this.status = status;
        this.user_id = user_id;
        this.time = time;
        this.desc = desc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCurrent_amount() {
        return current_amount;
    }

    public void setCurrent_amount(String current_amount) {
        this.current_amount = current_amount;
    }

    public String getRequest_amount() {
        return request_amount;
    }

    public void setRequest_amount(String request_amount) {
        this.request_amount = request_amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
