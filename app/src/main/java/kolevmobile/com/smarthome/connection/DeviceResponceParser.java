package kolevmobile.com.smarthome.connection;

import kolevmobile.com.smarthome.model.Device;


public interface DeviceResponceParser {

    String ERROR_KEY = "Error";

    void updateDevice(Device device, String responce);
}
