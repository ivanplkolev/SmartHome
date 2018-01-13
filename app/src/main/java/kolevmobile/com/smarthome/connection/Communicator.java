package kolevmobile.com.smarthome.connection;

import kolevmobile.com.smarthome.model.Device;
import kolevmobile.com.smarthome.model.RelayModel;

/**
 * Created by x on 19.11.2017 г..
 */

public interface Communicator {

    void getDeviceStatus(Device device);

    void switchRelay(Device device, RelayModel relayModel);
}
