//package kolevmobile.com.smarthome.old_new_connector;
//
//
//import android.os.Handler;
//import android.os.Message;
//
//import kolevmobile.com.smarthome.green_dao.entities.DHTDevice;
//import kolevmobile.com.smarthome.old_new_connector.responces.DHTSensorDataResponce;
//
///**
// * Created by me on 29/10/2017.
// */
//
//public class ReadDHTSensorTask implements Task {
//
//    DHTDevice device;
//    Handler handler;
//
//    public ReadDHTSensorTask(DHTDevice device, Handler handler) {
//        this.device = device;
//        this.handler = handler;
//    }
//
//
//    @Override
//    public void execute() {
//        DHTSensorDataResponce dhtSensorResponce = null;
//        try {
//            StringBuilder rawResponce = InternetConnector.readUrl(device.getUrl());
//            if (rawResponce == null || rawResponce.length() == 0) {
//                throw new NoDeviceException();
//            }
//            dhtSensorResponce = DHTResponceParser.parseResponce(rawResponce);
//        } catch (NoDeviceException e) {
//            dhtSensorResponce = DHTSensorDataResponce.getDHTSensorDataSurrogateWithError(CommunicatorError.CONNECTION_ERROR);
//        } catch (kolevmobile.com.smarthome.old_onnector.WrongSensorDataException e) {
//            dhtSensorResponce = DHTSensorDataResponce.getDHTSensorDataSurrogateWithError(CommunicatorError.READING_DATA_ERROR);
//        }
//        Message message = new Message();
//        message.what =;
//        message.arg1 = (int) device.getId().longValue();
//        handler.sendMessage(message);
//
//    }
//}
