package kolevmobile.com.smarthome.connection;

import kolevmobile.com.smarthome.main.MainActivity;
import kolevmobile.com.smarthome.model.Device;
import kolevmobile.com.smarthome.model.RelayModel;

/**
 * Created by x on 19.11.2017 г..
 */

public interface Communicator {

    void getDeviceStatus(Device device, MainActivity activity);

    void switchRelay(Device device, MainActivity activity, RelayModel relayModel);
}
