package kolevmobile.com.smarthome.main;

import android.os.Handler;
import android.os.Message;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import kolevmobile.com.smarthome.connection.Communicator;
import kolevmobile.com.smarthome.connection.DeviceResponceParser;
import kolevmobile.com.smarthome.di.CommunicatorComponent;
import kolevmobile.com.smarthome.di.CommunicatorModule;
import kolevmobile.com.smarthome.di.DaggerCommunicatorComponent;
import kolevmobile.com.smarthome.model.DaoSession;
import kolevmobile.com.smarthome.model.Device;
import kolevmobile.com.smarthome.model.DeviceDao;
import kolevmobile.com.smarthome.model.Error;
import kolevmobile.com.smarthome.model.RelayModel;
import kolevmobile.com.smarthome.model.RelayModelDao;
import kolevmobile.com.smarthome.model.RelayStatusDao;
import kolevmobile.com.smarthome.model.SensorModelDao;
import kolevmobile.com.smarthome.model.SensorValueDao;

public class MainPresenterImpl implements MainPresenter {

    private List<Device> activeDevices;
    private DeviceDao deviceDao;
    private SensorModelDao sensorModelDao;
    private SensorValueDao sensorValueDao;
    private RelayModelDao relayModelDao;
    private RelayStatusDao relayStatusDao;
    private DaoSession daoSession;

    private Handler mainHandler;

    @Inject
    DeviceResponceParser parser;

    @Inject
    Communicator communicator;

    public MainPresenterImpl(DaoSession daoSession) {
        this.daoSession = daoSession;
        this.deviceDao = daoSession.getDeviceDao();
        this.sensorModelDao = daoSession.getSensorModelDao();
        this.sensorValueDao = daoSession.getSensorValueDao();
        this.relayModelDao = daoSession.getRelayModelDao();
        this.relayStatusDao = daoSession.getRelayStatusDao();
    }


    @Override
    public void onCreate() {
        CommunicatorComponent component = DaggerCommunicatorComponent
                .builder()
                .communicatorModule(new CommunicatorModule(this)).build();
        component.inject(this);
        activeDevices = new ArrayList<>();

        new Thread() {
            public void run() {
                activeDevices.clear();
                activeDevices.addAll(deviceDao.loadAll());

                Message message = new Message();
                message.obj = activeDevices;
                message.what = MainActivity.MainHandler.DO_INIT_DEVICES;
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
                Message message = new Message();
                message.what = MainActivity.MainHandler.DO_UPDATE_ALL_VIEWS;
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
        message.what = MainActivity.MainHandler.DO_UPDATE_DEVICE_VIEW;
        mainHandler.sendMessage(message);
        communicator.getDeviceStatus(device);
    }

    @Override
    public void removeDevice(int position) {
        Device removedDevice = activeDevices.remove(position);
        Message message = new Message();
        message.obj = position;
        message.what = MainActivity.MainHandler.DO_REMOVE_DEVICE_VIEW;
        mainHandler.sendMessage(message);
        deviceDao.delete(removedDevice);
    }

    @Override
    public void switchDeviceRelay(int position, int subPosition, boolean isChecked) {
        Device updatingDevice = activeDevices.get(position);
        updatingDevice.setRefreshing(true);
        Message message = new Message();
        message.obj = updatingDevice;
        message.what = MainActivity.MainHandler.DO_UPDATE_DEVICE_VIEW;
        mainHandler.sendMessage(message);
        RelayModel updatingRelay = updatingDevice.getRelayModelList().get(subPosition);
        int newStatus = isChecked ? 1 : 0;
        updatingRelay.getActualStatus().setValue(newStatus);
        communicator.switchRelay(activeDevices.get(position), updatingRelay);
    }


    public void updateDevice(Device device, String responce) {
        parser.updateDevice(device, responce);

        device.setRefreshing(false);
        Message message = new Message();
        message.obj = device;
        message.what = MainActivity.MainHandler.DO_UPDATE_DEVICE_VIEW;
        mainHandler.sendMessage(message);
    }

    public List<Device> getActiveDevices() {
        return activeDevices;
    }

    public void updateDevice(Device device, Error error) {
        device.setError(Error.COMUNICATING_ERROR);
        Message message = new Message();
        message.obj = device;
        message.what = MainActivity.MainHandler.DO_UPDATE_DEVICE_VIEW;
        mainHandler.sendMessage(message);

    }

    @Override
    public DaoSession getDaoSession() {
        return daoSession;
    }

    public void setMainHandler(Handler mainHandler) {
        this.mainHandler = mainHandler;
    }

}
