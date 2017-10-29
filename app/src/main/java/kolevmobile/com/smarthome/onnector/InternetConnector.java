package kolevmobile.com.smarthome.onnector;

import kolevmobile.com.smarthome.data_base.Device;
import kolevmobile.com.smarthome.data_base.SensorData;

/**
 * Created by x on 15.10.2017 г..
 */

public interface InternetConnector {

    SensorData getData(Device deice) throws WrongSensorDataException, NoDeviceException, InterruptedException;
}
