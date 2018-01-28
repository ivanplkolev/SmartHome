package kolevmobile.com.smarthome.main;

import android.content.Context;
import android.os.Handler;

import java.util.List;

import kolevmobile.com.smarthome.Presenter;
import kolevmobile.com.smarthome.model.Device;
import kolevmobile.com.smarthome.model.Error;


public interface MainPresenter extends Presenter{

    void onCreate();

    void onResume();

    void onDestroy();


    void updateDevice();

    void refreshDevices();

    void refreshDevice(int pos);

    void removeDevice(int position);

    void switchDeviceRelay(int position, int subPosition, boolean isChecked);


//    Device getDeviceAt(int pos);

    public List<Device> getActiveDevices();
    void updateDevice(Device device, String responceString);

    void updateDevice(Device device, Error error);

    public void setMainHandler(Handler mainHandler) ;
    public void setContext(Context mainHandler) ;
    }
