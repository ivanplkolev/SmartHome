package kolevmobile.com.smarthome.widget;

import android.content.Context;
import android.content.Intent;

import java.util.List;

import javax.inject.Inject;

import kolevmobile.com.smarthome.connection.Communicator;
import kolevmobile.com.smarthome.connection.DeviceResponceParser;
import kolevmobile.com.smarthome.di.CommunicatorComponent;
import kolevmobile.com.smarthome.di.CommunicatorModule;
import kolevmobile.com.smarthome.di.DaggerCommunicatorComponent;
import kolevmobile.com.smarthome.model.DaoSession;
import kolevmobile.com.smarthome.model.Device;
import kolevmobile.com.smarthome.model.Error;
import kolevmobile.com.smarthome.model.RelayModel;
import kolevmobile.com.smarthome.model.SensorModel;
import kolevmobile.com.smarthome.model.Widget;
import kolevmobile.com.smarthome.model.WidgetDao;


public class WidgetPresenterImpl implements WidgetPresenter {


    private DaoSession daoSession;
    private WidgetDao widgetDao;

    @Inject
    Communicator communicator;

    @Inject
    DeviceResponceParser parser;

    private Context context;


    public WidgetPresenterImpl(DaoSession daoSession) {


//        this.daoSession = daoSession;
//        this.deviceDao = daoSession.getDeviceDao();
//        this.sensorModelDao = daoSession.getSensorModelDao();
//        this.sensorValueDao = daoSession.getSensorValueDao();
//        this.relayModelDao = daoSession.getRelayModelDao();
//        this.relayStatusDao = daoSession.getRelayStatusDao();
        this.widgetDao = daoSession.getWidgetDao();

        CommunicatorComponent component = DaggerCommunicatorComponent
                .builder()
                .communicatorModule(new CommunicatorModule(this)).build();

        component.inject(this);
    }

//    @Override
//    public void onUpdate(int widgetId) {
//
//        int a = 15;
////        Widget widget = widgetDao.load((long)widgetId);
////        Device updatingDevice;
////
////        switch (widget.getType()) {
////            case Widget.TYPE_DEVICE:
////                updatingDevice = widget.getDevice();
////                break;
////            case Widget.TYPE_SENSOR:
////                updatingDevice = widget.getSensorModel().getDeviceId();
////                break;
////            case Widget.TYPE_RELAY:
////                updatingDevice = widget.getRelayModel().getDeviceId();
////                break;
////        }
////
////        updatingDevice.setRefreshing(true);
////        Message message = new Message();
////        message.obj = updatingDevice;
////        message.what = MainActivity.MainHandler.DO_UPDATE_DEVICE_VIEW;
////        mainHandler.sendMessage(message);
////        RelayModel updatingRelay = updatingDevice.getRelayModelList().get(subPosition);
////        int newStatus = isChecked ? 1 : 0;
////        updatingRelay.getActualStatus().setValue(newStatus);
////        communicator.switchRelay(activeDevices.get(position), updatingRelay);
//    }


    @Override
    public void onClick(int widgetId) {
        Widget widget = widgetDao.queryBuilder().where(WidgetDao.Properties.WidgetiD.eq(widgetId)).uniqueOrThrow();
        if (widget.getDevice() == null) {
            initWidget(widgetId);
        }
        switch (widget.getType()) {
            case Widget.TYPE_SENSOR:
                refreshSensor(widget);
            case Widget.TYPE_RELAY:
                switchRelay(widget);
        }
    }

    @Override
    public void initWidget(int widgetId) {
        Intent serviceIntent = new Intent(context, WidgetSettingsActivity.class);
        serviceIntent.putExtra(WidgetService.WIDGET_ID, widgetId);
        context.startService(serviceIntent);
    }

    @Override
    public void finishInitWidget(Widget widget, SensorModel sensorModel) {
        if (widget.getType() != Widget.TYPE_SENSOR) {
            throw new IllegalStateException("Wrong widget type required Sensor Widget, not found");
        }
        widget.setSensorModelId(sensorModel.getId());
        widget.setDeviceId(sensorModel.getDeviceId());
        widgetDao.update(widget);
    }

    @Override
    public void finishInitWidget(Widget widget, RelayModel relayModel) {
        if (widget.getType() != Widget.TYPE_RELAY) {
            throw new IllegalStateException("Wrong widget type required Relay Widget, not found");
        }
        widget.setSensorModelId(relayModel.getId());
        widget.setDeviceId(relayModel.getDeviceId());
        widgetDao.update(widget);
    }

    @Override
    public void refreshSensor(Widget widget) {
        Device device = widget.getDevice();
        device.setRefreshing(true);
        // update all device views
        communicator.getDeviceStatus(device);
    }

    @Override
    public void switchRelay(Widget widget) {
        Device device = widget.getDevice();
        device.setRefreshing(true);
        RelayModel relayModel = widget.getRelayModel();
        boolean wasChecked = relayModel.getActualStatus().getValue() == 1;
        relayModel.getActualStatus().setValue(wasChecked ? 0 : 1);
        // update all device views
        communicator.switchRelay(device, relayModel);
    }

    @Override
    public void createWidget(int widgetId, int widgetType) {
        Widget widget = new Widget();
        widget.setWidgetiD(widgetId);
        widget.setType(widgetType);
        widgetDao.insert(widget);
    }

    @Override
    public void deleteWidget(int widgetId) {
        Widget deletingWidget = widgetDao.queryBuilder().where(WidgetDao.Properties.WidgetiD.eq(widgetId)).uniqueOrThrow();
        widgetDao.delete(deletingWidget);
    }

    @Override
    public void updateDevice(Device device, String responceString) {
        parser.updateDevice(device, responceString);


        //update All view about that device
        device.setRefreshing(false);
        Message message = new Message();
        message.obj = device;
        message.what = MainActivity.MainHandler.DO_UPDATE_DEVICE_VIEW;
        mainHandler.sendMessage(message);
    }

    @Override
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


//    private void refresh() {
//
////        Intent intent = new Intent(context, WidgetProvider.class);
////        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
////// Use an array and EXTRA_APPWIDGET_IDS instead of AppWidgetManager.EXTRA_APPWIDGET_ID,
////// since it seems the onUpdate() is only fired on that:
////        int[] ids = AppWidgetManager.getInstance(context)  .getAppWidgetI‌​ds(new ComponentName(getApplication(), WidgetProvider.class));
////        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
////        context.sendBroadcast(intent);
////
////        ////////////
////
////
////        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
////        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_2x1);
////        ComponentName thisWidget = new ComponentName(context, WidgetProvider.class);
////        remoteViews.setTextViewText(R.id.my_text_view, "myText" + System.currentTimeMillis());
////        appWidgetManager.updateAppWidget(thisWidget, remoteViews);
//
//    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public List<Device> loadAllDevices() {
        return daoSession.getDeviceDao().loadAll();
    }
}
