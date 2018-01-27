package kolevmobile.com.smarthome.widget;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.opengl.Visibility;
import android.view.View;
import android.widget.RemoteViews;

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
import kolevmobile.com.smarthome.model.Error;
import kolevmobile.com.smarthome.model.RelayModel;
import kolevmobile.com.smarthome.model.RelayWidget;
import kolevmobile.com.smarthome.model.RelayWidgetDao;
import kolevmobile.com.smarthome.model.SensorModel;
import kolevmobile.com.smarthome.model.SensorWidget;
import kolevmobile.com.smarthome.model.SensorWidgetDao;
import kolevmobile.com.smarthome.model.WidgetDao;


public class WidgetPresenterImpl implements WidgetPresenter {


    private DaoSession daoSession;
    private SensorWidgetDao sensorWidgetDao;
    private RelayWidgetDao relayWidgetDao;

    @Inject
    Communicator communicator;

    @Inject
    DeviceResponceParser parser;

    private Context context;


    public WidgetPresenterImpl(DaoSession daoSession) {
        this.sensorWidgetDao = daoSession.getSensorWidgetDao();
        this.relayWidgetDao = daoSession.getRelayWidgetDao();
        CommunicatorComponent component = DaggerCommunicatorComponent
                .builder()
                .communicatorModule(new CommunicatorModule(this)).build();
        component.inject(this);
    }


    @Override
    public void onClick(int widgetId, int widgetType) {
        switch (widgetType) {
            case WidgetProvider.WIDGET_TYPE_SENSOR:
                refreshSensor(widgetId);
            case WidgetProvider.WIDGET_TYPE_RELAY:
                switchRelay(widgetId);
        }
    }

    @Override
    public void initWidget(int widgetId) {
        Intent serviceIntent = new Intent(context, WidgetSettingsActivity.class);
        serviceIntent.putExtra(WidgetService.WIDGET_ID, widgetId);
        context.startService(serviceIntent);
    }

    @Override
    public void finishInitWidget(long widgetId, int WidgetType, Object model) {
        if (WidgetType == WidgetProvider.WIDGET_TYPE_SENSOR) {
            SensorModel sensorModel = (SensorModel) model;
            SensorWidget sensorWidget = sensorWidgetDao.load(widgetId);
            sensorWidget.setSensorModelId(sensorModel.getId());
            sensorWidget.setDeviceId(sensorModel.getDeviceId());
            sensorWidgetDao.update(sensorWidget);
        } else if (WidgetType == WidgetProvider.WIDGET_TYPE_RELAY) {
            RelayModel relayModel = (RelayModel) model;
            RelayWidget relayWidget = relayWidgetDao.load(widgetId);
            relayWidget.setRelayModelId(relayModel.getId());
            relayModel.setDeviceId(relayModel.getDeviceId());
            relayWidgetDao.update(relayWidget);
        }
    }

    private void refreshSensor(long widgetId) {
        SensorWidget widget = sensorWidgetDao.queryBuilder()
                .where(SensorWidgetDao.Properties.WidgetiD.eq(widgetId)).uniqueOrThrow();
        Device device = widget.getDevice();
        device.setRefreshing(true);
        updateAllDeviceViews(device);
        communicator.getDeviceStatus(device);
    }

    private void switchRelay(long widgetId) {
        RelayWidget widget = relayWidgetDao.queryBuilder()
                .where(RelayWidgetDao.Properties.WidgetiD.eq(widgetId)).uniqueOrThrow();
        Device device = widget.getDevice();
        device.setRefreshing(true);
        RelayModel relayModel = widget.getRelayModel();
        boolean wasChecked = relayModel.getActualStatus().getValue() == 1;
        relayModel.getActualStatus().setValue(wasChecked ? 0 : 1);
        updateAllDeviceViews(device);
        communicator.switchRelay(device, relayModel);
    }

    @Override
    public void createWidget(int widgetId, int widgetType) {
        if (widgetType == WidgetProvider.WIDGET_TYPE_SENSOR) {
            SensorWidget widget = new SensorWidget();
            widget.setWidgetiD(widgetId);
            sensorWidgetDao.insert(widget);
        } else if (widgetType == WidgetProvider.WIDGET_TYPE_RELAY) {
            RelayWidget widget = new RelayWidget();
            widget.setWidgetiD(widgetId);
            relayWidgetDao.insert(widget);
        }
    }

    @Override
    public void deleteWidget(int widgetId, int widgetType) {
        if (widgetType == WidgetProvider.WIDGET_TYPE_SENSOR) {
            SensorWidget deletingWidget = sensorWidgetDao.queryBuilder().where(SensorWidgetDao.Properties.WidgetiD.eq(widgetId)).uniqueOrThrow();
            sensorWidgetDao.delete(deletingWidget);
        } else if (widgetType == WidgetProvider.WIDGET_TYPE_RELAY) {
            RelayWidget deletingWidget = relayWidgetDao.queryBuilder().where(RelayWidgetDao.Properties.WidgetiD.eq(widgetId)).uniqueOrThrow();
            relayWidgetDao.delete(deletingWidget);
        }
    }

    @Override
    public void updateDevice(Device device, String responceString) {
        parser.updateDevice(device, responceString);
        device.setRefreshing(false);
        updateAllDeviceViews(device);
    }

    @Override
    public void updateDevice(Device device, Error error) {
        device.setError(Error.COMUNICATING_ERROR);
        device.setRefreshing(false);
        updateAllDeviceViews(device);
    }

    @Override
    public DaoSession getDaoSession() {
        return daoSession;
    }


    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public List<Device> loadAllDevices() {
        return daoSession.getDeviceDao().loadAll();
    }

    private void updateAllDeviceViews(Device device) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

        List<SensorWidget> widgets = sensorWidgetDao.queryBuilder().where(SensorWidgetDao.Properties.DeviceId.eq(device.getId())).list();
        for (SensorWidget widget : widgets) {
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.sensor_widget_layout);
            remoteViews.setViewVisibility(R.id.initSensorWidget, View.GONE);
            remoteViews.setViewVisibility(R.id.sensorWidgetCardview, View.VISIBLE);
            remoteViews.setTextViewText(R.id.sensor_name, widget.getSensorModel().getName());
            remoteViews.setTextViewText(R.id.sensor_value, widget.getSensorModel().getActualValue().getValue().toString());
            remoteViews.setTextViewText(R.id.sensor_units, widget.getSensorModel().getUnits());
            appWidgetManager.updateAppWidget(widget.getWidgetiD(), remoteViews);
        }

        List<RelayWidget> relayModels = relayWidgetDao.queryBuilder().where(RelayWidgetDao.Properties.DeviceId.eq(device.getId())).list();
        for (RelayWidget widget : relayModels) {
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.relay_widget_layout);
            remoteViews.setViewVisibility(R.id.initRelayWidget, View.GONE);
            remoteViews.setViewVisibility(R.id.relayWidgetCardView, View.VISIBLE);
            remoteViews.setTextViewText(R.id.relay_toggler_name, widget.getRelayModel().getName());
            remoteViews.setViewVisibility(R.id.relay_toggler_on, widget.getRelayModel().getActualStatus().getValue() == 1f ? View.VISIBLE : View.INVISIBLE);
            remoteViews.setViewVisibility(R.id.relay_toggler_off, widget.getRelayModel().getActualStatus().getValue() == 0f ? View.VISIBLE : View.INVISIBLE);
            appWidgetManager.updateAppWidget(widget.getWidgetiD(), remoteViews);
        }

    }
}
