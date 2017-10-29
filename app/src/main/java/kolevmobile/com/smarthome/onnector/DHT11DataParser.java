package kolevmobile.com.smarthome.onnector;

import java.util.Date;

import kolevmobile.com.smarthome.data_base.SensorData;

/**
 * Created by x on 15.10.2017 г..
 */

public class DHT11DataParser implements ArduinoDataParser {

    @Override
    public SensorData parseData(StringBuilder sb, int deviceUid) throws WrongSensorDataException {
        SensorData sensorData = null;
        try {
            if (sb == null || sb.length() == 0) {
                throw new WrongSensorDataException();
            }
            String[] res = sb.toString().split(" ");
            float temperature = Float.parseFloat(res[1]);
            float humidity = Float.parseFloat(res[0]);
            sensorData = new SensorData(temperature, humidity, new Date(), deviceUid);
        } catch (NumberFormatException e) {
            throw new WrongSensorDataException();
        }
        return sensorData;
    }
}
