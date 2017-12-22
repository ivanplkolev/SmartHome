package kolevmobile.com.smarthome.connection.model;

/**
 * Created by me on 05/12/2017.
 */

public class RelayModel {
  private  String key;
   private int value;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
