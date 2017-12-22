package kolevmobile.com.smarthome.connection.model;

import java.util.List;

/**
 * Created by me on 05/12/2017.
 */

public class Device {
    private String error;
    private List<SensorModel> sensorData;
    private List<RelayModel> relayData;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<SensorModel> getSensorData() {
        return sensorData;
    }

    public void setSensorData(List<SensorModel> sensorData) {
        this.sensorData = sensorData;
    }

    public List<RelayModel> getRelayData() {
        return relayData;
    }

    public void setRelayData(List<RelayModel> relayData) {
        this.relayData = relayData;
    }
}
