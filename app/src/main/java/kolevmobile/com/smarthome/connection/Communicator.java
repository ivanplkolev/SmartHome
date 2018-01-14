package kolevmobile.com.smarthome.connection;

import kolevmobile.com.smarthome.model.Device;
import kolevmobile.com.smarthome.model.RelayModel;

public interface Communicator {

    void getDeviceStatus(Device device);

    void switchRelay(Device device, RelayModel relayModel);
}
