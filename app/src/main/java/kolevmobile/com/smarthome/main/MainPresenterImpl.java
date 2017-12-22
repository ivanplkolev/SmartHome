package kolevmobile.com.smarthome.main;

import android.os.Handler;
import android.os.Message;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import kolevmobile.com.smarthome.connection.Communicator;
import kolevmobile.com.smarthome.connection.model.SensorModel;
import kolevmobile.com.smarthome.di.CommunicatorComponent;
import kolevmobile.com.smarthome.di.DaggerCommunicatorComponent;
import kolevmobile.com.smarthome.model.DaoSession;
import kolevmobile.com.smarthome.model.Device;
import kolevmobile.com.smarthome.model.DeviceDao;
import kolevmobile.com.smarthome.model.Error;
import kolevmobile.com.smarthome.model.RelayModel;
import kolevmobile.com.smarthome.model.RelayModelDao;
import kolevmobile.com.smarthome.model.RelayStatus;
import kolevmobile.com.smarthome.model.RelayStatusDao;
import kolevmobile.com.smarthome.model.SensorModelDao;
import kolevmobile.com.smarthome.model.SensorValue;
import kolevmobile.com.smarthome.model.SensorValueDao;

/**
 * Created by me on 30/11/2017.
 */

public class MainPresenterImpl implements MainPresenter {

    private static List<Device> activeDevices;

    private DeviceDao deviceDao;
    private SensorModelDao sensorModelDao;
    private SensorValueDao sensorValueDao;
    private RelayModelDao relayModelDao;
    private RelayStatusDao relayStatusDao;

    private Handler mainHandler;

    @Inject
    Communicator communicator;

    public MainPresenterImpl(DaoSession daoSession) {
        this.deviceDao = daoSession.getDeviceDao();
        this.sensorModelDao = daoSession.getSensorModelDao();
        this.sensorValueDao = daoSession.getSensorValueDao();
        this.relayModelDao = daoSession.getRelayModelDao();
        this.relayStatusDao = daoSession.getRelayStatusDao();
    }


    @Override
    public void onCreate() {
//        deviceDao = daoSession.getDeviceDao();

        CommunicatorComponent component = DaggerCommunicatorComponent.builder().build();
        component.inject(this);
        communicator.setPresenter(this);

        activeDevices = new ArrayList<>();

        new Thread() {
            public void run() {
                activeDevices.clear();
                activeDevices.addAll(deviceDao.loadAll());

                Message message = new Message();
                message.obj = activeDevices;
                message.what = MainActivity.DO_INIT_DEVICES;
                mainHandler.sendMessage(message);
            }
        }.start();

    }

    @Override
    public void onResume() {
        refreshDevices();
    }

    @Override
    public void onDestroy() {

    }


    @Override
    public void updateDevice() {

    }

    @Override
    public void refreshDevices() {
        new Thread() {
            public void run() {
                activeDevices.clear();
                activeDevices.addAll(deviceDao.loadAll());
//                mainDisplayAdapter.notifyDataSetChanged();
                Message message = new Message();
                message.what = MainActivity.DO_UPDATE_ALL_VIEWS;
                mainHandler.sendMessage(message);
            }
        }.start();
    }

    @Override
    public void refreshDevice(int pos) {
        Device device = activeDevices.get(pos);
        device.setRefreshing(true);
        Message message = new Message();
        message.obj = device;
        message.what = MainActivity.DO_UPDATE_DEVICE_VIEW;
        mainHandler.sendMessage(message);
        communicator.getDeviceStatus(device);
    }

    @Override
    public void removeDevice(int position) {
        Device removedDevice = activeDevices.remove(position);
//        mainDisplayAdapter.notifyItemRemoved(position);
        Message message = new Message();
        message.obj = position;
        message.what = MainActivity.DO_REMOVE_DEVICE_VIEW;
        mainHandler.sendMessage(message);
        deviceDao.delete(removedDevice);
    }

    @Override
    public void switchDeviceRelay(int position, int subPosition, boolean isChecked) {
        Device updatingDevice = activeDevices.get(position);
        updatingDevice.setRefreshing(true);
//        mainDisplayAdapter.notifyItemChanged(updatingDevice);
        Message message = new Message();
        message.obj = updatingDevice;
        message.what = MainActivity.DO_UPDATE_DEVICE_VIEW;
        mainHandler.sendMessage(message);
        RelayModel updatingRelay = updatingDevice.getRelayModelList().get(subPosition);
        int newStatus = isChecked ? 1 : 0;
        updatingRelay.getActualStatus().setValue(newStatus);
        communicator.switchRelay(activeDevices.get(position), updatingRelay);
    }


//    @Override
//    public Device getDeviceAt(int pos) {
//        return activeDevices.get(pos);
//    }


    public void updateDevice(Device device, kolevmobile.com.smarthome.connection.model.Device connectorDevice) {
        String error = connectorDevice.getError();

        if (error != null && error.length() != 0) {
            device.setError(Error.fromString(error));
        } else {
            List<SensorModel> sensorData = connectorDevice.getSensorData();
            for (SensorModel model : sensorData) {
                for (kolevmobile.com.smarthome.model.SensorModel dbModel : device.getSensorModelList()) {
                    if (model.getKey().equals(dbModel.getKey())) {
                        SensorValue sensorValue = new SensorValue();
                        sensorValue.setMeasuredAt(new Date());
                        sensorValue.setValue(model.getValue());
                        sensorValue.setSensorModelId(dbModel.getId());
                        sensorValueDao.insert(sensorValue);
                        device.setActualizationDate(new Date());
                        dbModel.setActualValue(sensorValue);
                        dbModel.setActualValueId(sensorValue.getId());
                        dbModel.getSensroValueList().add(sensorValue);
                        sensorModelDao.update(dbModel);
                        break;
                    }
                }
            }

            List<kolevmobile.com.smarthome.connection.model.RelayModel> relayStatusData = connectorDevice.getRelayData();
            for (kolevmobile.com.smarthome.connection.model.RelayModel model : relayStatusData) {
                for (kolevmobile.com.smarthome.model.RelayModel dbModel : device.getRelayModelList()) {
                    if (model.getKey().equals(dbModel.getKey())) {
                        RelayStatus relayStatus = new RelayStatus();
                        relayStatus.setSentAt(new Date());
                        relayStatus.setValue(model.getValue());
                        relayStatus.setRelayModelId(dbModel.getId());
                        relayStatusDao.insert(relayStatus);
                        device.setActualizationDate(new Date());
                        dbModel.setActualStatus(relayStatus);
                        dbModel.setActualStatusId(relayStatus.getId());
                        dbModel.getRelayStatusListlList().add(relayStatus);
                        relayModelDao.update(dbModel);
                        break;
                    }
                }
            }
        }
        device.setRefreshing(false);
        Message message = new Message();
        message.obj = device;
        message.what = MainActivity.DO_UPDATE_DEVICE_VIEW;
        mainHandler.sendMessage(message);
        deviceDao.update(device);
    }

    public List<Device> getActiveDevices() {
        return activeDevices;
    }

    public void updateDevice(Device device, Error error) {
        device.setError(Error.COMUNICATING_ERROR);
        Message message = new Message();
        message.obj = device;
        message.what = MainActivity.DO_UPDATE_DEVICE_VIEW;
        mainHandler.sendMessage(message);

    }

    public void setMainHandler(Handler mainHandler) {
        this.mainHandler = mainHandler;
    }
}
