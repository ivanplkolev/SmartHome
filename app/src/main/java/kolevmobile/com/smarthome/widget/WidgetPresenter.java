package kolevmobile.com.smarthome.widget;

import android.content.Context;

import java.util.List;

import kolevmobile.com.smarthome.Presenter;
import kolevmobile.com.smarthome.model.Device;

public interface WidgetPresenter extends Presenter {

    void onClick(int widgetId, int widgetType);

    void initWidget(int widgetId, int wigetType);

    void finishInitWidget(long widgetId, int WidgetType, Object model);

    void createWidget(int widgetId, int widgetType);

    void deleteWidget(int widgetId, int widgetType);

    public void setContext(Context context);

    List<Device> loadAllDevices();
}