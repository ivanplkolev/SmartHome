package kolevmobile.com.smarthome.add_edit_device;

/**
 * Created by me on 04/11/2017.
 */

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

import javax.inject.Inject;

import kolevmobile.com.smarthome.App;
import kolevmobile.com.smarthome.R;
import kolevmobile.com.smarthome.model.SensorModel;

public class DeviceSenosrsFragment extends Fragment {


    @Inject
    AddEditPresenter presenter;

    private RecyclerView recyclerView;
    private SensorsAdapter mAdapter;
    private FloatingActionButton addSensorModelFab;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.device_sensors_layout, container, false);
        ((App) getActivity().getApplication()).getPresenterComponent().inject(this);

        recyclerView = view.findViewById(R.id.sensors_list_view);
        addSensorModelFab = view.findViewById(R.id.add_sensor_model_fab);

        mAdapter = new SensorsAdapter(presenter.getDevice().getSensorModelList());

        mAdapter.setOnItemViewClickListener((view12, position, subPosition) -> {
            switch (view12.getId()) {
                case R.id.editButton:
                    addEditSensorModel(position);
                    break;
                case R.id.deleteButton:
                    deleteSensorModel(position);
                    break;
            }
        });

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        addSensorModelFab.setOnClickListener(view1 -> addEditSensorModel(-1));
        return view;
    }


    public void addEditSensorModel(int pos) {

        LayoutInflater li = LayoutInflater.from(getActivity());
        View promptsView = li.inflate(R.layout.add_sensor_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setView(promptsView);

        final EditText sensorNameInput = promptsView.findViewById(R.id.sensor_name_input);
        final EditText sensorDescriptionInput = promptsView.findViewById(R.id.sensor_description_input);
        final EditText sensorKeyInput = promptsView.findViewById(R.id.sensor_key_input);
        final EditText sensorUnitsInput = promptsView.findViewById(R.id.sensor_units_input);

        if (pos != -1) {
            SensorModel sensorModelIn = presenter.getDevice().getSensorModelList().get(pos);
            sensorNameInput.setText(sensorModelIn.getName());
            sensorDescriptionInput.setText(sensorModelIn.getDescription());
            sensorKeyInput.setText(sensorModelIn.getKey());
            sensorUnitsInput.setText(sensorModelIn.getUnits());

        }
        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        (dialog, id) -> {
                            String name = sensorNameInput.getText().toString();
                            String description = sensorNameInput.getText().toString();
                            String key = sensorNameInput.getText().toString();
                            String units = sensorNameInput.getText().toString();
                            presenter.addEditSensorModel(pos, name, description, key, units);

                            if (pos == -1) {
                                mAdapter.notifyItemRangeChanged(presenter.getDevice().getSensorModelList().size() - 1, 1);
                            } else {
                                mAdapter.notifyItemChanged(pos);
                            }
                        })
                .setNegativeButton("Cancel",
                        (dialog, id) -> dialog.cancel());

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void deleteSensorModel(int position) {
        presenter.deleteSensorModel(position);
        mAdapter.notifyItemRemoved(position);
    }


}
