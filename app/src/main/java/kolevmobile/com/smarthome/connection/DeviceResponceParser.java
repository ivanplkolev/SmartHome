package kolevmobile.com.smarthome.connection;

import kolevmobile.com.smarthome.model.Device;

/**
 * Created by x on 13.1.2018 г..
 */

public interface DeviceResponceParser {

    String ERROR_KEY = "Error";

    void updateDevice(Device device, String responce);
}
