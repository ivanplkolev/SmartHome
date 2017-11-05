package kolevmobile.com.smarthome;

/**
 * Created by me on 04/11/2017.
 */


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kolevmobile.com.smarthome.model.Device;

public class TabFragment3 extends Fragment {

    private RecyclerView recyclerView;
    private RelaysAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.device_relays_layout, container, false);

        recyclerView = view.findViewById(R.id.relays_list_view);

        mAdapter = new RelaysAdapter(getDevice().getRelayModelList());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        return view;
    }

    private Device getDevice() {
        return ((AddEditDeviceActivity) getActivity()).getDevice();
    }

}
