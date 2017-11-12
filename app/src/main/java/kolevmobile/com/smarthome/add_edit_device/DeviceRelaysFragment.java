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
import kolevmobile.com.smarthome.model.RelayModel;
import kolevmobile.com.smarthome.model.RelayModelDao;

public class DeviceRelaysFragment extends Fragment {

    private RecyclerView recyclerView;
    private RelaysAdapter mAdapter;


    private FloatingActionButton addRelayModelFab;
    DaoSession daoSession;
    RelayModelDao relayModelDao;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.device_relays_layout, container, false);

        recyclerView = view.findViewById(R.id.relays_list_view);

        addRelayModelFab = view.findViewById(R.id.add_relay_model_fab);


        mAdapter = new RelaysAdapter(getDevice().getRelayModelList());

        mAdapter.setOnItemViewClickListener(new ItemButtonObserver() {
            @Override
            public void onClick(View view, int position, int subPosition) {
                switch (view.getId()) {
                    case R.id.editButton:
                        addEditRelayModel(getDevice().getRelayModelList().get(position), position);
                        break;
                    case R.id.deleteButton:
                        deleteRelayModel(position);
                        break;
                }
            }
        });

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        addRelayModelFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeviceRelaysFragment.this.addEditRelayModel(null, 0);
            }
        });

        daoSession = ((App) getActivity().getApplication()).getDaoSession();
        relayModelDao = daoSession.getRelayModelDao();
        
        return view;
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


    private Device getDevice() {
        return ((AddEditDeviceActivity) getActivity()).getDevice();
    }

}
