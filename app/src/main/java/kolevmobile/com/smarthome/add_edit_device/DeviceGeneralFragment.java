package kolevmobile.com.smarthome.add_edit_device;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import kolevmobile.com.smarthome.R;
import kolevmobile.com.smarthome.model.Device;

/**
 * Created by me on 04/11/2017.
 */

public class DeviceGeneralFragment extends Fragment {

    //textfields
    EditText deviceName;
    EditText deviceDescription;
    EditText deviceUrl;
    EditText devicePort;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.device_general_layout, container, false);
        initializeFields(view);
        return view;
    }

    private void initializeFields(View view) {
        deviceName = view.findViewById(R.id.device_name_input);
        deviceDescription = view.findViewById(R.id.device_description_input);
        deviceUrl = view.findViewById(R.id.device_url_input);
        devicePort = view.findViewById(R.id.device_port_input);
    }

    public void copyToFields(Device device) {
        deviceName.setText(device.getName());
        deviceDescription.setText(device.getDescription());
        deviceUrl.setText(device.getUrlAddress());
        devicePort.setText(device.getPort().toString());
    }

    public void copyfromFields(Device device) {
        device.setName(deviceName.getText().toString());
        device.setDescription(deviceDescription.getText().toString());
        device.setUrlAddress(deviceUrl.getText().toString());
        device.setPort(Integer.parseInt(devicePort.getText().toString()));
    }
}
