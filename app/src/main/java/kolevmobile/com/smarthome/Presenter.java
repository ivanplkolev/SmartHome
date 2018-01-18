package kolevmobile.com.smarthome;

import kolevmobile.com.smarthome.model.DaoSession;
import kolevmobile.com.smarthome.model.Device;
import kolevmobile.com.smarthome.model.Error;

/**
 * Created by x on 16.1.2018 г..
 */

public interface Presenter {

    void updateDevice(Device device, String responceString);

    void updateDevice(Device device, Error error);

    DaoSession getDaoSession();
}
