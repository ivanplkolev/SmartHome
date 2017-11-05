package kolevmobile.com.smarthome;

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

import kolevmobile.com.smarthome.model.DaoSession;
import kolevmobile.com.smarthome.model.Device;
import kolevmobile.com.smarthome.model.SensorModel;
import kolevmobile.com.smarthome.model.SensorModelDao;

public class TabFragment2 extends Fragment {

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
            public void onClick(View view, int position) {
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
                TabFragment2.this.addEditSensorModel(null, 0);
            }
        });

        daoSession = ((App) getActivity().getApplication()).getDaoSession();
        sensorModelDao = daoSession.getSensorModelDao();

        return view;
    }


    private void addEditSensorModel(final SensorModel sensorModelIn, final int pos) {

        LayoutInflater li = LayoutInflater.from(getActivity());
        View promptsView = li.inflate(R.layout.add_sensor_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);


        final EditText sensorNameInput = promptsView.findViewById(R.id.sensor_name_input);
        final EditText sensorDescriptionInput = promptsView.findViewById(R.id.sensor_description_input);
        final EditText sensorKeyInput = promptsView.findViewById(R.id.sensor_key_input);
        final EditText sensorUnitsInput = promptsView.findViewById(R.id.sensor_units_input);

        if (sensorModelIn != null) {
            sensorNameInput.setText(sensorModelIn.getName());
            sensorDescriptionInput.setText(sensorModelIn.getDescription());
            sensorKeyInput.setText(sensorModelIn.getKey());
            sensorUnitsInput.setText(sensorModelIn.getUnits());

        }
        final SensorModel sensorModel = sensorModelIn != null ? sensorModelIn : new SensorModel();
        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                sensorModel.setName(sensorNameInput.getText().toString());
                                sensorModel.setDescription(sensorDescriptionInput.getText().toString());
                                sensorModel.setKey(sensorKeyInput.getText().toString());
                                sensorModel.setUnits(sensorUnitsInput.getText().toString());
                                sensorModel.setDeviceId(getDevice().getId());


                                if (sensorModelIn == null) {
                                    getDevice().getSensorModelList().add(sensorModel);
                                    mAdapter.notifyItemRangeChanged(getDevice().getSensorModelList().size() - 1, 1);
                                    sensorModelDao.insert(sensorModel);
                                } else {
                                    mAdapter.notifyItemChanged(pos);
                                    sensorModelDao.update(sensorModel);
                                }
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }


    private void deleteSensorModel(int position) {
        SensorModel deleteingEntity = getDevice().getSensorModelList().remove(position);
        mAdapter.notifyItemRemoved(position);
        sensorModelDao.delete(deleteingEntity);
    }

    private Device getDevice() {
        return ((AddEditDeviceActivity) getActivity()).getDevice();
    }

}
