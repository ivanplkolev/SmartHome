package kolevmobile.com.smarthome.main;

import android.os.Handler;

import java.util.List;

import kolevmobile.com.smarthome.model.Device;
import kolevmobile.com.smarthome.model.Error;

/**
 * Created by me on 30/11/2017.
 */

public interface MainPresenter {

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
    }
