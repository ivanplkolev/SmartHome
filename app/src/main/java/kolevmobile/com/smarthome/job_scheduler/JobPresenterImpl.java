package kolevmobile.com.smarthome.job_scheduler;

import android.appwidget.AppWidgetManager;
import android.content.Context;
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
import kolevmobile.com.smarthome.model.DeviceDao;
import kolevmobile.com.smarthome.model.Error;
import kolevmobile.com.smarthome.model.RelayWidget;
import kolevmobile.com.smarthome.model.RelayWidgetDao;
import kolevmobile.com.smarthome.model.SensorWidget;
import kolevmobile.com.smarthome.model.SensorWidgetDao;

public class JobPresenterImpl implements JobPresenter {


    private DaoSession daoSession;

    private DeviceDao deviceDao;
    private RelayWidgetDao relayWidgetDao;
    private SensorWidgetDao sensorWidgetDao;


    @Inject
    Communicator communicator;

    @Inject
    DeviceResponceParser parser;

    public JobPresenterImpl(DaoSession daoSession) {
        this.daoSession = daoSession;
        this.deviceDao = daoSession.getDeviceDao();
        this.relayWidgetDao = daoSession.getRelayWidgetDao();
        this.sensorWidgetDao = daoSession.getSensorWidgetDao();

        CommunicatorComponent component = DaggerCommunicatorComponent
                .builder()
                .communicatorModule(new CommunicatorModule(this)).build();
        component.inject(this);
    }


    public void updateDevices() {
        List<Device> devices = deviceDao.loadAll();
        for(Device device : devices){
            communicator.getDeviceStatus(this, device);
        }
    }

    private Context context;

    @Override
    public void setContext(Context context) {
        this.context = context;
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

    private void updateAllDeviceViews(Device device) {
        if (context == null) {
            return;
        }
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

        List<SensorWidget> sensorWidgets = sensorWidgetDao.queryBuilder().where(SensorWidgetDao.Properties.DeviceId.eq(device.getId())).list();
        for (SensorWidget widget : sensorWidgets) {
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.sensor_widget_layout);
            remoteViews.setViewVisibility(R.id.initSensorWidget, View.GONE);
            remoteViews.setViewVisibility(R.id.sensorWidgetCardview, View.VISIBLE);
            remoteViews.setTextViewText(R.id.sensor_device_name, device.getName());

            remoteViews.setTextViewText(R.id.sensor_name, widget.getSensorModel().getName());
            remoteViews.setTextViewText(R.id.sensor_value, widget.getSensorModel().getActualValue().getValue().toString());
            remoteViews.setTextViewText(R.id.sensor_units, widget.getSensorModel().getUnits());
            appWidgetManager.updateAppWidget(widget.getWidgetiD(), remoteViews);
        }

        List<RelayWidget> relayWidgets = relayWidgetDao.queryBuilder().where(RelayWidgetDao.Properties.DeviceId.eq(device.getId())).list();
        for (RelayWidget widget : relayWidgets) {
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.relay_widget_layout);
            remoteViews.setViewVisibility(R.id.initRelayWidget, View.GONE);
            remoteViews.setViewVisibility(R.id.relayWidgetCardView, View.VISIBLE);
            remoteViews.setViewVisibility(R.id.relay_toggler_info, View.INVISIBLE);
            remoteViews.setTextViewText(R.id.relay_toggler_device_name, device.getName());

            remoteViews.setTextViewText(R.id.relay_toggler_name, widget.getRelayModel().getName());
            remoteViews.setViewVisibility(R.id.relay_toggler_on, widget.getRelayModel().getActualStatus().getValue() == 1f ? View.VISIBLE : View.INVISIBLE);
            remoteViews.setViewVisibility(R.id.relay_toggler_off, widget.getRelayModel().getActualStatus().getValue() == 0f ? View.VISIBLE : View.INVISIBLE);
            appWidgetManager.updateAppWidget(widget.getWidgetiD(), remoteViews);
        }

    }

}
