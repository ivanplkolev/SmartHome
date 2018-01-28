package kolevmobile.com.smarthome.connection;

import kolevmobile.com.smarthome.Presenter;
import kolevmobile.com.smarthome.model.Device;
import kolevmobile.com.smarthome.model.RelayModel;

public interface Communicator {

    void getDeviceStatus(Presenter presenter, Device device);

    void switchRelay(Presenter presenter, Device device, RelayModel relayModel);
}
