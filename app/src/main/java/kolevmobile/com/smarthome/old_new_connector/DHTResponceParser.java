//package kolevmobile.com.smarthome.old_new_connector;
//
//import java.util.Date;
//import kolevmobile.com.smarthome.old_new_connector.responces.DHTSensorDataResponce;
//import kolevmobile.com.smarthome.old_onnector.WrongSensorDataException;
//
///**
// * Created by x on 15.10.2017 г..
// */
//
//public class DHTResponceParser {
//
//    public static DHTSensorDataResponce parseResponce(StringBuilder sb) throws WrongSensorDataException {
//        DHTSensorDataResponce dhtSensorData = null;
//        try {
//            String[] res = sb.toString().split(" ");
//            float temperature = Float.parseFloat(res[1]);
//            float humidity = Float.parseFloat(res[0]);
//            dhtSensorData = DHTSensorDataResponce.getDHTSensorDataSurrogate(temperature, humidity, new Date());
//        } catch (NumberFormatException e) {
//            throw new WrongSensorDataException();
//        }
//        return dhtSensorData;
//    }
//}
