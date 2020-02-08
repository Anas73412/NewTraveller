package in.binplus.travel.Model;

public class TransactionModel {
    String amount ;
    String id ;
    String status ;
    String date ;
    String time ;
    String description ;

    public TransactionModel() {
    }

    public TransactionModel(String amount, String id, String status, String date, String time, String description) {
        this.amount = amount;
        this.id = id;
        this.status = status;
        this.date = date;
        this.time = time;
        this.description = description;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
