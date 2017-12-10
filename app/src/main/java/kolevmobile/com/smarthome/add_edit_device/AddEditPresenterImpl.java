package kolevmobile.com.smarthome.add_edit_device;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import kolevmobile.com.smarthome.App;
import kolevmobile.com.smarthome.R;
import kolevmobile.com.smarthome.model.DaoSession;
import kolevmobile.com.smarthome.model.Device;
import kolevmobile.com.smarthome.model.DeviceDao;
import kolevmobile.com.smarthome.model.RelayModel;
import kolevmobile.com.smarthome.model.SensorModel;

/**
 * Created by me on 03/12/2017.
 */

public class AddEditPresenterImpl implements AddEditPresenter {

    Device device;
    DeviceDao deviceDao;

    Activity context;

    AddEditPresenterImpl(Activity context){
        this.context=context;
    }


    protected void onCreate(){

        DaoSession daoSession = ((App) context.getApplication()).getDaoSession();
        deviceDao = daoSession.getDeviceDao();
    }

    public void saveDevice(View v) {

        if (device == null) {
            device = new Device();
            deviceGeneralFragment.copyfromFields(device);
            deviceDao.insert(device);
        } else {
            deviceGeneralFragment.copyfromFields(device);
            deviceDao.update(device);
        }

        if (adapter.getCount() == 1) {


            TabLayout tabLayout = findViewById(R.id.tab_layout);
            tabLayout.addTab(tabLayout.newTab().setText("Sensors"));
            tabLayout.addTab(tabLayout.newTab().setText("Realays"));
            tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

            viewPager = findViewById(R.id.pager);
            adapter = new DeviceFragmentPagerAdapter(getSupportFragmentManager(), deviceGeneralFragment, deviceSenosrsFragment, deviceRelaysFragment);
            viewPager.setAdapter(adapter);
        }

        viewPager.setCurrentItem(1, true);
    }

    private void addEditRelayModel(final RelayModel relayModelIn, final int pos) {

        LayoutInflater li = LayoutInflater.from(getActivity());
        View promptsView = li.inflate(R.layout.add_relay_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);


        final EditText relayNameInput = promptsView.findViewById(R.id.relay_name_input);
        final EditText relayDescriptionInput = promptsView.findViewById(R.id.relay_description_input);
        final EditText relayKeyInput = promptsView.findViewById(R.id.relay_key_input);
//        final EditText relayUnitsInput = promptsView.findViewById(R.id.relay_units_input);

        if (relayModelIn != null) {
            relayNameInput.setText(relayModelIn.getName());
            relayDescriptionInput.setText(relayModelIn.getDescription());
            relayKeyInput.setText(relayModelIn.getKey());
//            relayUnitsInput.setText(relayModelIn.getUnits());

        }
        final RelayModel relayModel = relayModelIn != null ? relayModelIn : new RelayModel();
        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                relayModel.setName(relayNameInput.getText().toString());
                                relayModel.setDescription(relayDescriptionInput.getText().toString());
                                relayModel.setKey(relayKeyInput.getText().toString());
//                                relayModel.setUnits(relayUnitsInput.getText().toString());
                                relayModel.setDeviceId(getDevice().getId());


                                if (relayModelIn == null) {
                                    getDevice().getRelayModelList().add(relayModel);
                                    mAdapter.notifyItemRangeChanged(getDevice().getRelayModelList().size() - 1, 1);
                                    relayModelDao.insert(relayModel);
                                } else {
                                    mAdapter.notifyItemChanged(pos);
                                    relayModelDao.update(relayModel);
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


    private void deleteRelayModel(int position) {
        RelayModel deleteingEntity = getDevice().getRelayModelList().remove(position);
        mAdapter.notifyItemRemoved(position);
        relayModelDao.delete(deleteingEntity);
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




}
