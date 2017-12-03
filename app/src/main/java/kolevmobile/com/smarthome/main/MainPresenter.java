package kolevmobile.com.smarthome.main;

import android.os.Handler;

import kolevmobile.com.smarthome.model.Device;

/**
 * Created by me on 30/11/2017.
 */

public interface MainPresenter {

    void onCreate();
    void onResume();
    void onDestroy();

    void addDevice();
    void editDevice(int pos);
    void updateDevice();
    void refreshDevices();
    void refreshDevice(int  pos);
    void removeDevice(int position);

    void switchDeviceRelay(int position, int subPosition, boolean isChecked);
    void showDeviceDetails(int position);

//    Device getDeviceAt(int pos);
}
