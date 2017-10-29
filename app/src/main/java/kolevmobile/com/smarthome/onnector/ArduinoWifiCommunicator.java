package kolevmobile.com.smarthome.onnector;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import kolevmobile.com.smarthome.data_base.Device;
import kolevmobile.com.smarthome.data_base.SensorData;

/**
 * Created by x on 15.10.2017 г..
 */

public class ArduinoWifiCommunicator implements InternetConnector {
    ArduinoDataParser parser;

    @Override
    public SensorData getData(Device device) throws WrongSensorDataException, NoDeviceException {
        if (parser == null) {
            parser = new DHT11DataParser();
        }
        StringBuilder sb  = new StringBuilder();
            try {
                URL url = new URL(device.getUrlAdress());
                URLConnection connection = url.openConnection();
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String returnString = "";
                while ((returnString = in.readLine()) != null) {
                    sb.append(returnString);
                }
                in.close();
            } catch (Exception e) {
                throw new NoDeviceException();
            }
        return parser.parseData(sb, device.getUid());
    }
}
