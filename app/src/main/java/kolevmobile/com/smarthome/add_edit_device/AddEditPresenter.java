package kolevmobile.com.smarthome.add_edit_device;

import kolevmobile.com.smarthome.model.Device;

/**
 * Created by me on 03/12/2017.
 */

public interface AddEditPresenter {


    Device getDevice();

    void init(Long deviceId);

    void deleteSensorModel(int pos);

    void addEditSensorModel(final int pos, String name, String desc, String key, String units);

    void deleteRelayModel(int position);

    void saveDeviceRelayModel(int pos, String relayModelName, String relayModeldesc, String key);

    Device createDevice();

    void updateDevice();
}
