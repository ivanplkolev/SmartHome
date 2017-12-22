package kolevmobile.com.smarthome.add_edit_device;

import kolevmobile.com.smarthome.model.DaoSession;
import kolevmobile.com.smarthome.model.Device;
import kolevmobile.com.smarthome.model.DeviceDao;
import kolevmobile.com.smarthome.model.RelayModel;
import kolevmobile.com.smarthome.model.RelayModelDao;
import kolevmobile.com.smarthome.model.SensorModel;
import kolevmobile.com.smarthome.model.SensorModelDao;

/**
 * Created by me on 03/12/2017.
 */

public class AddEditPresenterImpl implements AddEditPresenter {

    Device device;

    DeviceDao deviceDao;
    SensorModelDao sensorModelDao;
    RelayModelDao relayModelDao;

    public AddEditPresenterImpl(DaoSession daoSession) {
        deviceDao = daoSession.getDeviceDao();
        sensorModelDao = daoSession.getSensorModelDao();
        relayModelDao = daoSession.getRelayModelDao();
    }

    public void init(Long deviceId) {
        if (deviceId != 0L) {
            device = deviceDao.load(deviceId);
        }
    }

    public Device getDevice() {
        return device;
    }

    public Device createDevice() {
        if (this.device != null) {
            throw new IllegalStateException();
        }
        this.device = new Device();
        deviceDao.insert(device);
        return device;
    }

    public void updateDevice() {
        deviceDao.update(device);
    }


    public void addEditSensorModel(int pos, String name, String desc, String key, String units) {
        final SensorModel sensorModel = pos != -1 ? device.getSensorModelList().get(pos) : new SensorModel();
        sensorModel.setName(name);
        sensorModel.setDescription(desc);
        sensorModel.setKey(key);
        sensorModel.setUnits(units);
        sensorModel.setDeviceId(device.getId());
        if (pos == -1) {
            device.getSensorModelList().add(sensorModel);
            sensorModelDao.insert(sensorModel);
        } else {
            sensorModelDao.update(sensorModel);
        }
    }

    public void deleteSensorModel(int position) {
        SensorModel deleteingEntity = device.getSensorModelList().remove(position);
        sensorModelDao.delete(deleteingEntity);
    }

    public void saveDeviceRelayModel(int pos, String relayModelName, String relayModeldesc, String key) {
        RelayModel relayModel = pos != -1 ? device.getRelayModelList().get(pos) : new RelayModel();
        relayModel.setName(relayModelName);
        relayModel.setDescription(relayModeldesc);
        relayModel.setKey(key);
        relayModel.setDeviceId(device.getId());
        if (pos == -1) {
            device.getRelayModelList().add(relayModel);
            relayModelDao.insert(relayModel);
        } else {
            relayModelDao.update(relayModel);
        }
    }

    public void deleteRelayModel(int position) {
        RelayModel deleteingEntity = device.getRelayModelList().remove(position);
        relayModelDao.delete(deleteingEntity);
    }


}
