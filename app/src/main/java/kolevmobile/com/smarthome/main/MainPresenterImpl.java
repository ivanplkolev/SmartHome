package kolevmobile.com.smarthome.main;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.RemoteViews;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import kolevmobile.com.smarthome.R;
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
import kolevmobile.com.smarthome.model.RelayWidget;
import kolevmobile.com.smarthome.model.RelayWidgetDao;
import kolevmobile.com.smarthome.model.SensorModelDao;
import kolevmobile.com.smarthome.model.SensorValueDao;
import kolevmobile.com.smarthome.model.SensorWidget;
import kolevmobile.com.smarthome.model.SensorWidgetDao;

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
        communicator.getDeviceStatus(this, device);
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
        communicator.switchRelay(this, activeDevices.get(position), updatingRelay);
    }


    public void updateDevice(Device device, String responce) {
        parser.updateDevice(device, responce);

        device.setRefreshing(false);
        Message message = new Message();
        message.obj = device;
        message.what = MainActivity.MainHandler.DO_UPDATE_DEVICE_VIEW;
        mainHandler.sendMessage(message);


        updateAllDeviceViews(device);
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
    public void setContext(Context
                                   context) {
        this.context = context;
    }

    private  Context context;


    private void updateAllDeviceViews(Device device) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

        List<SensorWidget> sensorWidgets = daoSession.getSensorWidgetDao().queryBuilder().where(SensorWidgetDao.Properties.DeviceId.eq(device.getId())).list();
        for (SensorWidget widget : sensorWidgets) {
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.sensor_widget_layout);
            remoteViews.setViewVisibility(R.id.initSensorWidget, View.GONE);
            remoteViews.setViewVisibility(R.id.sensorWidgetCardview, View.VISIBLE);
            remoteViews.setTextViewText(R.id.sensor_name, widget.getSensorModel().getName());
            remoteViews.setTextViewText(R.id.sensor_value, widget.getSensorModel().getActualValue().getValue().toString());
            remoteViews.setTextViewText(R.id.sensor_units, widget.getSensorModel().getUnits());
            appWidgetManager.updateAppWidget(widget.getWidgetiD(), remoteViews);
        }

        List<RelayWidget> relayWidgets = daoSession.getRelayWidgetDao().queryBuilder().where(RelayWidgetDao.Properties.DeviceId.eq(device.getId())).list();
        for (RelayWidget widget : relayWidgets) {
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.relay_widget_layout);
            remoteViews.setViewVisibility(R.id.initRelayWidget, View.GONE);
            remoteViews.setViewVisibility(R.id.relayWidgetCardView, View.VISIBLE);
            remoteViews.setViewVisibility(R.id.relay_toggler_info, View.INVISIBLE);
            remoteViews.setTextViewText(R.id.relay_toggler_name, widget.getRelayModel().getName());
            remoteViews.setViewVisibility(R.id.relay_toggler_on, widget.getRelayModel().getActualStatus().getValue() == 1f ? View.VISIBLE : View.INVISIBLE);
            remoteViews.setViewVisibility(R.id.relay_toggler_off, widget.getRelayModel().getActualStatus().getValue() == 0f ? View.VISIBLE : View.INVISIBLE);
            appWidgetManager.updateAppWidget(widget.getWidgetiD(), remoteViews);
        }

    }


}
