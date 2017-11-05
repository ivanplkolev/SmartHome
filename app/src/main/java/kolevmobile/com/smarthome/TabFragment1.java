package kolevmobile.com.smarthome;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import kolevmobile.com.smarthome.model.Device;

/**
 * Created by me on 04/11/2017.
 */

public class TabFragment1 extends Fragment {

    //textfields
    EditText deviceName;
    EditText deviceDescription;
    EditText deviceUrl;
    EditText devicePort;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.device_general_layout, container, false);
        initializeFields(view);
        if (getDevice() != null) {
            copyToFields();
        }
        return inflater.inflate(R.layout.device_general_layout, container, false);
    }

    private void initializeFields(View view) {
        deviceName = view.findViewById(R.id.device_name_input);
        deviceDescription = view.findViewById(R.id.device_description_input);
        deviceUrl = view.findViewById(R.id.device_url_input);
        devicePort = view.findViewById(R.id.device_port_input);
    }

    private void copyToFields() {
        deviceName.setText(getDevice().getName());
        deviceDescription.setText(getDevice().getDescription());
        deviceUrl.setText(getDevice().getUrlAddress());
        devicePort.setText(getDevice().getPort().toString());
    }

    public void copyfromFields() {
        getDevice().setName(deviceName.getText().toString());
        getDevice().setDescription(deviceDescription.getText().toString());
        getDevice().setUrlAddress(deviceUrl.getText().toString());
        getDevice().setPort(Integer.parseInt(devicePort.getText().toString()));
    }

    private Device getDevice() {
        return ((AddEditDeviceActivity) getActivity()).getDevice();
    }

}
