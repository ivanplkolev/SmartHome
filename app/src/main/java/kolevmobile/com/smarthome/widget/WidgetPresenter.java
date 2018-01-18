package kolevmobile.com.smarthome.widget;

import android.content.Context;

import java.util.List;

import kolevmobile.com.smarthome.Presenter;
import kolevmobile.com.smarthome.model.Device;
import kolevmobile.com.smarthome.model.RelayModel;
import kolevmobile.com.smarthome.model.SensorModel;
import kolevmobile.com.smarthome.model.Widget;


public interface WidgetPresenter extends Presenter {

    void onClick(int widgetId);

    void initWidget(int widgetId);

    void finishInitWidget(Widget widget, SensorModel sensorModel);

    void finishInitWidget(Widget widget, RelayModel relayModel);

    void refreshSensor(Widget widget);

    void switchRelay(Widget widget);

    void createWidget(int widgetId, int widgetType);

    void deleteWidget(int widgetId);

    public void setContext(Context context);

    List<Device> loadAllDevices();
}
