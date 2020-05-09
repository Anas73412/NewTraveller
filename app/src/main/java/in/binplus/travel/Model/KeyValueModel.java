package in.binplus.travel.Model;

/**
 * Developed by Binplus Technologies pvt. ltd.  on 07,May,2020
 */
public class KeyValueModel {
    String key,value;

    public KeyValueModel(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
