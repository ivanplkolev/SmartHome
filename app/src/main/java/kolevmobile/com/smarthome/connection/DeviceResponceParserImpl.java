package kolevmobile.com.smarthome.connection;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.Date;

import kolevmobile.com.smarthome.model.DaoSession;
import kolevmobile.com.smarthome.model.Device;
import kolevmobile.com.smarthome.model.DeviceDao;
import kolevmobile.com.smarthome.model.Error;
import kolevmobile.com.smarthome.model.RelayModel;
import kolevmobile.com.smarthome.model.RelayModelDao;
import kolevmobile.com.smarthome.model.RelayStatus;
import kolevmobile.com.smarthome.model.RelayStatusDao;
import kolevmobile.com.smarthome.model.SensorModel;
import kolevmobile.com.smarthome.model.SensorModelDao;
import kolevmobile.com.smarthome.model.SensorValue;
import kolevmobile.com.smarthome.model.SensorValueDao;

public class DeviceResponceParserImpl implements DeviceResponceParser {


    private DeviceDao deviceDao;
    private SensorModelDao sensorModelDao;
    private SensorValueDao sensorValueDao;
    private RelayModelDao relayModelDao;
    private RelayStatusDao relayStatusDao;

    public DeviceResponceParserImpl(DaoSession daoSession) {
        this.deviceDao = daoSession.getDeviceDao();
        this.sensorModelDao = daoSession.getSensorModelDao();
        this.sensorValueDao = daoSession.getSensorValueDao();
        this.relayModelDao = daoSession.getRelayModelDao();
        this.relayStatusDao = daoSession.getRelayStatusDao();
    }


    public void updateDevice(Device device, String responce) {
        try {
            JsonElement jelement = new JsonParser().parse(responce);
            JsonObject jobject = jelement.getAsJsonObject();
            String errorString = jobject.get(ERROR_KEY).getAsString();
            if (errorString != null && errorString.length() != 0) {
                device.setError(Error.fromString(errorString));
                return;
            }
            Date currentDate = new Date();
            for (SensorModel sensorModel : device.getSensorModelList()) {
                float result = jobject.get(sensorModel.getKey()).getAsFloat();
                SensorValue sensorValue = new SensorValue();
                sensorValue.setMeasuredAt(currentDate);
                sensorValue.setValue(result);
                sensorValue.setSensorModelId(sensorModel.getId());
                sensorValueDao.insert(sensorValue);
                sensorModel.setActualValue(sensorValue);
                sensorModel.setActualValueId(sensorValue.getId());
                sensorModel.getSensroValueList().add(sensorValue);
                sensorModelDao.update(sensorModel);
            }
            for (RelayModel relayModel : device.getRelayModelList()) {
                int result = jobject.get(relayModel.getKey()).getAsInt();
                RelayStatus relayStatus = new RelayStatus();
                relayStatus.setRelayModelId(relayModel.getId());
                relayStatus.setValue(result);
                relayStatus.setSentAt(currentDate);
                relayStatusDao.insert(relayStatus);
                relayModel.setActualStatus(relayStatus);
                relayModel.setActualStatusId(relayStatus.getId());
                relayModelDao.update(relayModel);
            }
            device.setActualizationDate(currentDate);
            deviceDao.update(device);
        } catch (Exception e) {
            device.setError(Error.SCHEMA_ERROR);
        }
    }

}
