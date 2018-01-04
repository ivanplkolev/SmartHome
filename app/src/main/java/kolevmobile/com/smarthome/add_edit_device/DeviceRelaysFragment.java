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

import butterknife.BindView;
import butterknife.ButterKnife;
import kolevmobile.com.smarthome.App;
import kolevmobile.com.smarthome.R;
import kolevmobile.com.smarthome.model.RelayModel;

public class DeviceRelaysFragment extends Fragment {

    @Inject
    AddEditPresenter presenter;

    @BindView(R.id.relays_list_view)
    RecyclerView recyclerView;
    @BindView(R.id.add_relay_model_fab)
    FloatingActionButton addRelayModelFab;

    private RelaysAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.device_relays_layout, container, false);
        ButterKnife.bind(this, view);

        ((App) getActivity().getApplication()).getPresenterComponent().inject(this);


        mAdapter = new RelaysAdapter(presenter.getDevice().getRelayModelList());

        mAdapter.setOnItemViewClickListener((view1, position, subPosition) -> {
            switch (view1.getId()) {
                case R.id.editButton:
                    addEditRelayModel(position);
                    break;
                case R.id.deleteButton:
                    deleteRelayModel(position);
                    break;
            }
        });

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        addRelayModelFab.setOnClickListener(view12 -> DeviceRelaysFragment.this.addEditRelayModel(-1));
        return view;
    }

    public void addEditRelayModel(final int pos) {
        LayoutInflater li = LayoutInflater.from(getActivity());
        View promptsView = li.inflate(R.layout.add_relay_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setView(promptsView);

        final EditText relayNameInput = promptsView.findViewById(R.id.relay_name_input);
        final EditText relayDescriptionInput = promptsView.findViewById(R.id.relay_description_input);
        final EditText relayKeyInput = promptsView.findViewById(R.id.relay_key_input);

        if (pos != -1) {
            RelayModel relayModelIn = presenter.getDevice().getRelayModelList().get(pos);
            relayNameInput.setText(relayModelIn.getName());
            relayDescriptionInput.setText(relayModelIn.getDescription());
            relayKeyInput.setText(relayModelIn.getKey());
        }

        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        (dialog, id) -> {
                            String name = relayNameInput.getText().toString();
                            String description = relayDescriptionInput.getText().toString();
                            String key = relayKeyInput.getText().toString();
                            presenter.saveDeviceRelayModel(pos, name, description, key);
                            if (pos == -1) {
                                mAdapter.notifyItemRangeChanged(presenter.getDevice().getRelayModelList().size() - 1, 1);
                            } else {
                                mAdapter.notifyItemChanged(pos);
                            }
                        })
                .setNegativeButton("Cancel",
                        (dialog, id) -> dialog.cancel());
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void deleteRelayModel(int position) {
        presenter.deleteRelayModel(position);
        mAdapter.notifyItemRemoved(position);
    }


}
