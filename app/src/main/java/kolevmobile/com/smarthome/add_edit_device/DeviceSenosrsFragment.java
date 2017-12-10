package kolevmobile.com.smarthome.add_edit_device;

/**
 * Created by me on 04/11/2017.
 */

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import kolevmobile.com.smarthome.App;
import kolevmobile.com.smarthome.ItemButtonObserver;
import kolevmobile.com.smarthome.R;
import kolevmobile.com.smarthome.model.DaoSession;
import kolevmobile.com.smarthome.model.Device;
import kolevmobile.com.smarthome.model.SensorModel;
import kolevmobile.com.smarthome.model.SensorModelDao;

public class DeviceSenosrsFragment extends Fragment {

    private RecyclerView recyclerView;
    private SensorsAdapter mAdapter;
    private FloatingActionButton addSensorModelFab;

    DaoSession daoSession;
    SensorModelDao sensorModelDao;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.device_sensors_layout, container, false);

        recyclerView = view.findViewById(R.id.sensors_list_view);
        addSensorModelFab = view.findViewById(R.id.add_sensor_model_fab);

        mAdapter = new SensorsAdapter(getDevice().getSensorModelList());

        mAdapter.setOnItemViewClickListener(new ItemButtonObserver() {
            @Override
            public void onClick(View view, int position, int subPosition) {
                switch (view.getId()) {
                    case R.id.editButton:
                        addEditSensorModel(getDevice().getSensorModelList().get(position), position);
                        break;
                    case R.id.deleteButton:
                        deleteSensorModel(position);
                        break;
                }
            }
        });


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        addSensorModelFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeviceSenosrsFragment.this.addEditSensorModel(null, 0);
            }
        });

        daoSession = ((App) getActivity().getApplication()).getDaoSession();
        sensorModelDao = daoSession.getSensorModelDao();

        return view;
    }





    private Device getDevice() {
        return ((AddEditDeviceActivity) getActivity()).getDevice();
    }

}
