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





    private Device getDevice() {
        return ((AddEditDeviceActivity) getActivity()).getDevice();
    }

}
