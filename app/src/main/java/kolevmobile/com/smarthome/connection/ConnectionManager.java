//package kolevmobile.com.smarthome.connection;
//
//import kolevmobile.com.smarthome.model.Device;
//import kolevmobile.com.smarthome.model.RelayData;
//import kolevmobile.com.smarthome.model.SensorData;
//import kolevmobile.com.smarthome.model.SensorModel;
//
///**
// * Created by me on 02/11/2017.
// */
//
//public class ConnectionManager {
//
//    public void makeActual(Device device) {
//
//        StringBuilder request = RequestBuilder.prepareActualisationResponce(device);
//
//        StringBuilder responce = NetworkConnector.connect(request, device);
//        ResponceParser.parseResponce(responce.toString(), device);
//    }
//
//    public void switchRelay(Device device) {
//        StringBuilder request = RequestBuilder.prepareSwitchRequest(device);
//        StringBuilder responce = NetworkConnector.connect(request, device);
//        ResponceParser.parseResponce(responce.toString(), device);
//    }
//
//}
